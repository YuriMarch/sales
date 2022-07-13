package com.letscode.service;

import com.letscode.client.ShopperClientService;
import com.letscode.exceptions.BusinessException;
import com.letscode.dto.FinishCartRequest;
import com.letscode.dto.Shopper;
import com.letscode.enumerator.Status;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinishCartService {
    private final SaleRepositoryService saleRepositoryService;
    private final ShopperClientService shopperClientService;

    public Mono<Cart> execute(FinishCartRequest finishCartRequest) {
        Mono<Cart> cart = saleRepositoryService.getCartById(finishCartRequest.getCartId());

        Mono<Shopper> shopper = shopperClientService.getShopper(finishCartRequest.getShopperId())
                .map(s -> verifyUserCpfAndPassword(s, finishCartRequest.getCpf(), finishCartRequest.getPassword()));

        return Mono.zip(cart, shopper)
                .map(tuple -> verifyShopperId(tuple.getT1(), tuple.getT2()))
                .flatMap(saleRepositoryService::saveCart);
    }

    private Cart verifyShopperId(Cart cart, Shopper shopper) {
        if (!Objects.equals(cart.getShopperId(), shopper.getId())) throw new BusinessException("ShoppeId is different from the cart");
        return cart;
    }

    private Shopper verifyUserCpfAndPassword(Shopper shopper, String cpf, String password) {
        if(!Objects.equals(shopper.getCpf(), cpf) || !Objects.equals(shopper.getPassword(), password)) throw new BusinessException("Validation error - CPF or password is incorrect");
        return shopper;
    }

    private void verifyCep(Cart cart){
        if (cart.getCep() == null) throw new BusinessException("Invalid CEP");
    }

    private void verifyProducts(Cart cart){
        if (cart.getProducts().isEmpty()) throw new BusinessException("Cart is empty");
    }

    private void verifyStatusCart(Cart cart){
        if (cart.getStatus() != Status.MOUNTING) throw new BusinessException("Cart was already finished");
    }

    private Cart updateStatusCart(Cart cart){
        cart.setStatus(Status.IN_TRANSIT);
        cart.setPurchaseDate(LocalDateTime.now());
        return cart;
    }

    private Cart setCart(Cart cart){
        verifyCep(cart);
        verifyProducts(cart);
        verifyStatusCart(cart);
        return updateStatusCart(cart);
    }
}