package com.example.fooddeliveryapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${client.mockserver.url}")
    private String mockServerUrl;

    @Bean("mockServerWebClient")
    public WebClient mockServerWebClient() {
        return WebClient
                .builder()
                .baseUrl(mockServerUrl)
                .build();
    }
}
