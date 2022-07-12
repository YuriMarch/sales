package com.letscode.service;

import com.letscode.client.ShopperClientService;
import com.letscode.dto.Shopper;
import com.letscode.dto.ShopperIdRequest;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateCartService {

    private final ShopperClientService shopperClientService;
    private final SaleRepositoryService saleRepositoryService;

    public Mono<Cart> execute(ShopperIdRequest shopperIdRequest){
        return shopperClientService.getShopper(shopperIdRequest.getShopperId())
                .map(this::createCart)
                .flatMap(saleRepositoryService::saveCart);
    }

    private Cart createCart(Shopper shopper){
        Cart cart = new Cart();
        cart.setShopperId(shopper.getId());
        return cart;
    }
}
