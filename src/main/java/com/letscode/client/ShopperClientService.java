package com.letscode.client;

import com.letscode.dto.Shopper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ShopperClientService {

    private final WebClient webClientShopper;

    @CircuitBreaker(name = "shopper-circuit-breaker")
    public Mono<Shopper> getShopper(String shopperId) {
        return this.webClientShopper
                .method(HttpMethod.GET)
                .uri("/shopper/{id}", shopperId)
                .retrieve()
                .bodyToMono(Shopper.class);
    }
}
