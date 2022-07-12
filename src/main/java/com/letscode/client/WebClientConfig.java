package com.letscode.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfig {

    @Value("${spring.data.viacep.uri}")
    private String cepClientUri;

    @Value("${spring.data.product-api.uri}")
    private String productClientUri;

    @Value("${spring.data.shopper-api.uri}")
    private String shopperClientUri;

    @Bean
    public WebClient webClientCep(WebClient.Builder builder) {
        return builder
                .baseUrl(cepClientUri)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient webClientProduct(WebClient.Builder builder) {
        return builder
                .baseUrl(productClientUri)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient webClientShopper(WebClient.Builder builder) {
        return builder
                .baseUrl(shopperClientUri)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
