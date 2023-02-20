package com.example.fooddeliveryapp.service.interfaces;

import com.example.fooddeliveryapp.model.Order;
import reactor.core.publisher.Mono;

public interface MockServerServiceI {

    Mono<Order> deliveryOrderToCustomer(final Order order);
}
