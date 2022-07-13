package com.letscode.controller;

import com.letscode.dto.*;
import com.letscode.model.Cart;
import com.letscode.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController {

    private final AddProductService addProductService;
    private final CepService cepService;
    private final CreateCartService createCartService;
    private final FinishCartService finishCartService;
    private final PriceService priceService;
    private final RemoveProductService removeProductService;
    private final SaleRepositoryService saleRepositoryService;
    private final StatusService statusService;

    @GetMapping
    public Flux<Cart> findAllCarts() {
        return saleRepositoryService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Mono<Cart> findCartById(String id) {
        return saleRepositoryService.getCartById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCartById(String id) {
        return saleRepositoryService.deleteCartById(id);
    }

    @PostMapping
    public Mono<Cart> createCart(@RequestBody ShopperIdRequest shopperIdRequest) {
        return createCartService.execute(shopperIdRequest);
    }

    @PutMapping("/addProduct")
    public Mono<Cart> addProduct(@RequestBody ProductRequest productRequest) {
        return addProductService.execute(productRequest);
    }

    @PutMapping("/removeProduct")
    public Mono<Cart> removeProduct(@RequestBody ProductRequest productRequest) {
        return removeProductService.execute(productRequest);
    }

    @PutMapping("/addCep")
    public Mono<Cart> addCep(@RequestBody CepRequest cepRequest) {
        return cepService.execute(cepRequest);
    }

    @PutMapping("/finishCart")
    public Mono<Cart> finishCart(@RequestBody FinishCartRequest finishCartRequest) {
        return finishCartService.execute(finishCartRequest);
    }

    @GetMapping("/shopperId/{id}")
    public Flux<Cart> findCartsByShopperId(@PathVariable String id) {
        return saleRepositoryService.getCartsByShopper(id);
    }

    @PutMapping("/status")
    public Mono<Cart> changeStatus(@RequestBody StatusRequest statusRequest) {
        return statusService.execute(statusRequest);
    }
}
