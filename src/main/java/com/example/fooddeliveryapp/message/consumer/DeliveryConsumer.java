package com.example.fooddeliveryapp.message.consumer;

import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.model.Error;
import com.example.fooddeliveryapp.repository.ErrorRepository;
import com.example.fooddeliveryapp.service.interfaces.OrderServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Component("onReceiveFoodOrdersToDelivery")
@RequiredArgsConstructor
public class DeliveryConsumer implements Consumer<OrderDto> {

    private final OrderServiceI orderService;
    private final ErrorRepository errorRepository;

    @Override
    public void accept(OrderDto order) {
        Mono.just(order)
                .flatMap(orderService::deliveryOrder)
                .log("DeliveryConsumer.accept", INFO, ON_NEXT, ON_ERROR)
                .onErrorResume(err -> {
                    final var error = Error.builder()
                            .errorType(err.getCause().getClass().getName())
                            .errorMessage(err.getMessage())
                            .origin("DeliveryConsumer")
                            .build();
                    errorRepository.save(error);
                    return Mono.empty();
                })
                .blockOptional();
    }
}
