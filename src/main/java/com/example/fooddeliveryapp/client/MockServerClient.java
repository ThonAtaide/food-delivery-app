package com.example.fooddeliveryapp.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Slf4j
@Component
public class MockServerClient {

    @Value("${client.mockserver.order-dispatch-uri}")
    private String mockServerOrderDispatcherUri;

    private WebClient webClient;

    public MockServerClient(@Qualifier("mockServerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Void> dispatchOrder(final String orderUuid) {
        log.info("Dispatching order {}", orderUuid);
        return webClient
                .post()
                .uri(it -> it.path(mockServerOrderDispatcherUri)
                        .path(orderUuid)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .log("MockServerClient.dispatchOrder", INFO, ON_NEXT, ON_ERROR)
                .then();
    }
}
