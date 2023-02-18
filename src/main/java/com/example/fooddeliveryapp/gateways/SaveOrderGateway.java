package com.example.fooddeliveryapp.gateways;

import com.example.fooddeliveryapp.gateways.interfaces.SaveOrderGatewayI;
import com.example.fooddeliveryapp.model.Order;
import com.example.fooddeliveryapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveOrderGateway implements SaveOrderGatewayI {

    private final OrderRepository orderRepository;

    public Mono<Order> saveOrder(final Order order) {
        log.info("Saving order {}", order);
        return Mono.just(order)
                .map(orderRepository::save)
                .subscribeOn(Schedulers.boundedElastic())
                .log("SaveOrderGateway.saveOrder", INFO, ON_NEXT, ON_ERROR);
    }
}
