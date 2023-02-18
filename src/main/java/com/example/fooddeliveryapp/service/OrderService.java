package com.example.fooddeliveryapp.service;

import com.example.fooddeliveryapp.client.MockServerClient;
import com.example.fooddeliveryapp.enums.OrderStatus;
import com.example.fooddeliveryapp.exceptions.OrderAlreadyFinishedException;
import com.example.fooddeliveryapp.exceptions.OrderNotFoundException;
import com.example.fooddeliveryapp.gateways.interfaces.FindOrderByIdGatewayI;
import com.example.fooddeliveryapp.gateways.interfaces.SaveOrderGatewayI;
import com.example.fooddeliveryapp.mapper.OrderMapper;
import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.message.producer.OrderProducer;
import com.example.fooddeliveryapp.model.Order;
import com.example.fooddeliveryapp.repository.OrderRepository;
import com.example.fooddeliveryapp.service.interfaces.OrderServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Optional;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {

    private final OrderProducer orderProducer;
    private final SaveOrderGatewayI saveOrderGatewayI;
    private final FindOrderByIdGatewayI findOrderByIdGatewayI;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final MockServerService mockServerService;

    @Override
    public Mono<OrderDto> createOrder(final OrderDto order) {
        log.info("Criando ordem {}", order);
        return Mono.just(order)
                .map(orderMapper::from)
                .flatMap(saveOrderGatewayI::saveOrder)
                .map(orderMapper::from)
                .flatMap(orderProducer::sendOrder)
                .log("OrderService.createOrder", INFO, ON_NEXT, ON_ERROR);
    }

    @Override
    public Mono<OrderDto> updateOrderStatus(final OrderDto orderDto, final OrderStatus orderStatus) {
        return Mono.just(orderDto)
                .flatMap(it -> findOrderByIdGatewayI.findOrderById(it.getId()))
                .filter(this::orderStatusIsNotFinished)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderAlreadyFinishedException(orderDto.getId()))))
                .flatMap(mockServerService::dispatchOrder)
                .doOnNext(it -> it.setOrderStatus(orderStatus))
                .flatMap(saveOrderGatewayI::saveOrder)
                .map(orderMapper::from)
                .log("OrderService.updateOrder", INFO, ON_NEXT, ON_ERROR);
    }

    private boolean orderStatusIsNotFinished(Order order) {
        return !OrderStatus.FINISHED.equals(order.getOrderStatus());
    }
}
