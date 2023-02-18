package com.example.fooddeliveryapp.gateways;

import com.example.fooddeliveryapp.exceptions.OrderNotFoundException;
import com.example.fooddeliveryapp.gateways.interfaces.FindOrderByIdGatewayI;
import com.example.fooddeliveryapp.model.Order;
import com.example.fooddeliveryapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindOrderByIdGateway implements FindOrderByIdGatewayI {

    private final OrderRepository orderRepository;

    public Mono<Order> findOrderById(final Long id) {
        log.info("Finding order from id {}", id);
        return Mono.just(id)
                .map(orderRepository::findById)
                .map(Optional::get)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderNotFoundException(id))))
                .subscribeOn(Schedulers.boundedElastic())
                .log("FindOrderByIdGateway.findOrderById", INFO, ON_NEXT, ON_ERROR);
    }
}
