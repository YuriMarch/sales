package com.letscode.repository;

import com.letscode.model.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SaleRepository extends ReactiveCrudRepository<Cart, String> {
    Flux<Cart> findByShopperId(String userId);
}
