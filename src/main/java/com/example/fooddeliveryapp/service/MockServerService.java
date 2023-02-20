package com.example.fooddeliveryapp.service;

import com.example.fooddeliveryapp.client.MockServerClient;
import com.example.fooddeliveryapp.model.Order;
import com.example.fooddeliveryapp.service.interfaces.MockServerServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockServerService implements MockServerServiceI {

    private final MockServerClient mockServerClient;

    @Override
    public Mono<Order> deliveryOrderToCustomer(Order order) {
        log.info("Calling mock server to dispatch order");
        return Mono.just(order)
                .flatMap(it -> mockServerClient.dispatchOrder(it.getCorrelationId()))
                .then(Mono.just(order));
    }
}
