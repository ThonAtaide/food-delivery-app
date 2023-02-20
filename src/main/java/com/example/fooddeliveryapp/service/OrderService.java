package com.example.fooddeliveryapp.service;

import com.example.fooddeliveryapp.exceptions.*;
import com.example.fooddeliveryapp.gateways.interfaces.FindOrderByIdGatewayI;
import com.example.fooddeliveryapp.gateways.interfaces.SaveOrderGatewayI;
import com.example.fooddeliveryapp.mapper.OrderMapper;
import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.message.producer.DeliveryOrderProducer;
import com.example.fooddeliveryapp.model.Order;
import com.example.fooddeliveryapp.service.interfaces.OrderServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.fooddeliveryapp.enums.OrderStatus.DELIVERED;
import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {

    private final DeliveryOrderProducer deliveryOrderProducer;
    private final SaveOrderGatewayI saveOrderGatewayI;
    private final FindOrderByIdGatewayI findOrderByIdGatewayI;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final MockServerService mockServerService;

    @Override
    public Mono<OrderDto> createOrder(final OrderDto order) {
        log.info("A ordem {} está sendo gerada...", order);
        return Mono.just(order)
                .map(orderMapper::from)
                .flatMap(saveOrderGatewayI::saveOrder)
                .map(orderMapper::from)
                .flatMap(deliveryOrderProducer::sendOrderToDelivery)
                .log("OrderService.createOrder", INFO, ON_NEXT, ON_ERROR);
    }

    @Override
    public Mono<OrderDto> deliveryOrder(final OrderDto order) {
        log.info("A ordem {} está saindo para a entrega...", order);
        return Mono.just(order)
                .flatMap(it -> findOrderByIdGatewayI.findOrderById(it.getId()))
                .filter(Order::orderStatusIsNotDelivered)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new OrderAlreadyDeliveredException(order.getId()))))
                .flatMap(mockServerService::deliveryOrderToCustomer)
                .doOnNext(it -> it.setOrderStatus(DELIVERED))
                .flatMap(saveOrderGatewayI::saveOrder)
                .map(orderMapper::from)
                .onErrorMap(OrderDeliveryException::new)
                .log("OrderService.deliveryOrder", INFO, ON_NEXT, ON_ERROR);
    }
}
