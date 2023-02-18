package com.example.fooddeliveryapp.message.consumer;

import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.service.interfaces.OrderServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static com.example.fooddeliveryapp.enums.OrderStatus.FINISHED;
import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Component("onReceiveFoodOrders")
@RequiredArgsConstructor
public class OrderConsumer implements Consumer<OrderDto> {

    private final OrderServiceI orderService;

    @Override
    public void accept(OrderDto order) {
        Mono.just(order)
                .flatMap(it -> orderService.updateOrderStatus(it, FINISHED))
                .log("OrderConsumer.accept", INFO, ON_NEXT, ON_ERROR)
                .blockOptional();
    }
}
