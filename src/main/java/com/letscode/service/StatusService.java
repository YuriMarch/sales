package com.letscode.service;

import com.letscode.controller.exceptions.BusinessException;
import com.letscode.dto.StatusRequest;
import com.letscode.enumerator.Status;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final SaleRepositoryService saleRepositoryService;

    public Mono<Cart> updateStatus(StatusRequest statusRequest){
        return saleRepositoryService.getCart(statusRequest.getCartId()).map(cart -> {
            if (cart.getStatus() != Status.IN_TRANSIT) throw new BusinessException("Cart can't be updated because it was already finished");
            cart.setStatus(statusRequest.getStatusCart());
            return cart;
        }).flatMap(saleRepositoryService::saveCart);
    }
}
