package com.letscode.service;

import com.letscode.controller.exceptions.BusinessException;
import com.letscode.controller.exceptions.NotExistException;
import com.letscode.enumerator.Status;
import com.letscode.model.Cart;
import com.letscode.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaleRepositoryService {

    private final SaleRepository saleRepository;

    public Flux<Cart> getCarts() {
        return saleRepository.findAll();
    }

    public Mono<Cart> getCart(String cartId) {
        return saleRepository.findById(cartId).switchIfEmpty(Mono.error(new NotExistException("Cart not found")));
    }

    public Mono<Void> deleteCart(String cartId) {
        return getCart(cartId).map(this::verifyDeleteConditions).flatMap(cart -> saleRepository.deleteById(cartId));
    }

    private Cart verifyDeleteConditions(Cart cart) {
        if (cart.getStatus() != Status.MOUNTING)
            throw new BusinessException("Cart can't be deleted because it was already finished");
        return cart;
    }

    public Mono<Cart> saveCart(Cart cart) {
        return saleRepository.save(cart);
    }

    public Flux<Cart> getCartsByShopper(String shopperId) {
        return saleRepository.findByShopperId(shopperId)
                .switchIfEmpty(Mono.error(new NotExistException("Cart not found for this shopper")));
    }
}



