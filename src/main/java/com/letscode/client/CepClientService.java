package com.letscode.client;

import com.letscode.dto.Cep;
import com.letscode.dto.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CepClientService {

    private final WebClient webClientCep;

    @CircuitBreaker(name = "cep-circuit-breaker")
    public Mono<Cep> getCep(String cep) {
        return this.webClientCep
                .method(HttpMethod.GET)
                .uri("/{cep}/json/", cep)
                .retrieve()
                .bodyToMono(Cep.class);
    }
}
