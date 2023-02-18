package com.example.fooddeliveryapp.gateways.interfaces;

import com.example.fooddeliveryapp.model.Order;
import reactor.core.publisher.Mono;

public interface FindOrderByIdGatewayI {

    Mono<Order> findOrderById(final Long id);
}
