package com.example.fooddeliveryapp.service.interfaces;

import com.example.fooddeliveryapp.enums.OrderStatus;
import com.example.fooddeliveryapp.message.dto.OrderDto;
import reactor.core.publisher.Mono;

public interface OrderServiceI {

    Mono<OrderDto> createOrder(OrderDto order);

    Mono<OrderDto> deliveryOrder(OrderDto order);

}
