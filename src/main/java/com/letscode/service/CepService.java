package com.letscode.service;

import com.letscode.client.CepClientService;
import com.letscode.exceptions.BusinessException;
import com.letscode.dto.Cep;
import com.letscode.dto.CepRequest;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CepService {

    private final CepClientService cepClientService;
    private final SaleRepositoryService saleRepositoryService;

     public Mono<Cart> execute(CepRequest cepRequest){
         Mono<Cep> cep = cepClientService.getCep(cepRequest.getCep());
         Mono<Cart> cart = saleRepositoryService.getCartById(cepRequest.getCartId());
         return Mono.zip(cep, cart)
                 .map(tuple -> this.setCep(tuple.getT1(), tuple.getT2()))
                 .flatMap(saleRepositoryService::saveCart);
     }

     private Cart setCep(Cep cep, Cart cart){
         if (cep.getCep() == null) throw new BusinessException("Invalid CEP");
         cart.setCep(cep);
         return cart;
     }
}
