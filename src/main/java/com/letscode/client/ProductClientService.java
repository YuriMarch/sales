package com.letscode.client;

import com.letscode.dto.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductClientService {

    private final WebClient webClientProduct;

    @CircuitBreaker(name = "product-circuit-breaker")
    public Mono<Product> getProduct(String productId) {
        return this.webClientProduct
                .method(HttpMethod.GET)
                .uri("/product/{id}", productId)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
