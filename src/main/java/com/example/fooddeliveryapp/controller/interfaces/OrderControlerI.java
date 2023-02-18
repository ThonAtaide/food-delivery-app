package com.example.fooddeliveryapp.controller.interfaces;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@RequestMapping("/order")
public interface OrderControlerI {

    @PostMapping
    Mono<Void> createRandomOrders(@RequestParam(name = "numberOfOrders", defaultValue = "1000") Integer numberOfOrders);
}
