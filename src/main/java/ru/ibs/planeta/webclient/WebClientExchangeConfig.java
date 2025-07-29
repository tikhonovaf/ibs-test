package ru.ibs.planeta.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientExchangeConfig {
    @Value("${loader.departments-url}")
    private String baseUrl;

    @Bean
    public WebClient webClientExchange(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
