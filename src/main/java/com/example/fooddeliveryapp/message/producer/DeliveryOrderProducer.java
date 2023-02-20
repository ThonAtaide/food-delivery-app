package com.example.fooddeliveryapp.message.producer;

import com.example.fooddeliveryapp.message.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryOrderProducer {

    public static final String BINDER = "delivery-orders-producer-out-0";

    private final StreamBridge streamBridge;

    public Mono<OrderDto> sendOrderToDelivery(OrderDto order) {
        log.info("Produzindo ordem {}", order);
        return Mono.just(order)
                .map(this::buildMessage)
                .map(it -> streamBridge.send(BINDER, it))
                .log("OrderProducer.sendOrder", INFO, ON_NEXT, ON_ERROR)
                .then(Mono.just(order));
    }

    private Message<OrderDto> buildMessage(OrderDto order) {
        return MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.MESSAGE_KEY, order.getCorrelationId().getBytes(StandardCharsets.UTF_8))
                .build();
    }
}
