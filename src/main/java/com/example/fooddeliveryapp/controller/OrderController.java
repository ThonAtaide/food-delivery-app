package com.example.fooddeliveryapp.controller;

import com.example.fooddeliveryapp.controller.interfaces.OrderControlerI;
import com.example.fooddeliveryapp.enums.OrderStatus;
import com.example.fooddeliveryapp.message.dto.OrderDto;
import com.example.fooddeliveryapp.service.interfaces.OrderServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.fooddeliveryapp.enums.OrderStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControlerI {

    private final OrderServiceI orderService;

    @Override
    public Mono<Void> createRandomOrders(final Integer numberOfOrders) {
       return Flux.range(0, numberOfOrders)
                .map(it -> buildRandomOrder())
                .flatMap(orderService::createOrder)
                .then();
    }

    private OrderDto buildRandomOrder() {
        return OrderDto.builder()
                .orderStatus(CREATED)
                .price(BigDecimal.valueOf(System.currentTimeMillis()))
                .product(UUID.randomUUID().toString())
                .correlationId(UUID.randomUUID().toString())
                .build();
    }
}
