package com.example.fooddeliveryapp.gateways.interfaces;

import com.example.fooddeliveryapp.model.Order;
import reactor.core.publisher.Mono;

public interface SaveOrderGatewayI {

    Mono<Order> saveOrder(final Order order);
}
