package com.letscode.service;

import com.letscode.exceptions.BusinessException;
import com.letscode.exceptions.NotExistException;
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

    public Flux<Cart> getAllCarts() {
        return saleRepository.findAll();
    }

    public Mono<Cart> getCartById(String cartId) {
        return saleRepository.findById(cartId).switchIfEmpty(Mono.error(new NotExistException("Cart not found")));
    }

    public Mono<Void> deleteCartById(String cartId) {
        return getCartById(cartId).map(this::verifyDeleteConditions).flatMap(cart -> saleRepository.deleteById(cartId));
    }

    public Cart verifyShopper(Cart cart, String userId){
        if(!cart.getShopperId().equals(userId)){
            throw new BusinessException("User Id does not match with cart user id");
        }
        return cart;
    }

    public void validateStatusCart(Cart cart){
        if(cart.getStatus() != Status.MOUNTING){
            throw new BusinessException("Cart is not in mounting status");
        }
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



