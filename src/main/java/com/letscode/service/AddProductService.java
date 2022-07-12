package com.letscode.service;

import com.letscode.client.ProductClientService;
import com.letscode.controller.exceptions.BusinessException;
import com.letscode.dto.Product;
import com.letscode.dto.ProductRequest;
import com.letscode.enumerator.Status;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddProductService {

    private final SaleRepositoryService saleRepositoryService;
    private final PriceService priceService;
    private final ProductClientService productClientService;

    public Mono<Cart> execute(ProductRequest productRequest){
        Mono<Cart> cart = saleRepositoryService.getCart(productRequest.getCartId())
                .map(cart1 -> verifyUser(cart1, productRequest.getShopperId()));

        Mono<Product> product = productClientService.getProduct(productRequest.getProductId())
                .map(product1 -> this.setProductQuantity(product1, productRequest.getQuantity()));

        return Mono.zip(cart, product)
                .map(t -> this.addProduct(t.getT1(), t.getT2()))
                .map(priceService::updateTotalPrice)
                .flatMap(saleRepositoryService::saveCart);
    }

    public Cart verifyUser(Cart cart, String userId){
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

    private Cart addProduct(Cart cart, Product product){
        validateStatusCart(cart);
        List<Product> products = cart.getProducts();
        List<String> productsId = products.stream().map(Product::getId).collect(Collectors.toList());
        if(productsId.contains(product.getId())){
            int index = productsId.indexOf(product.getId());
            Product prod = products.get(index);
            int newQuantity = prod.getQuantity() + product.getQuantity();
            prod.setQuantity(newQuantity);
            products.set(index, prod);
        } else {
            products.add(product);
        }
        cart.setProducts(products);
        return cart;
    }

    private Product setProductQuantity(Product product, int quantity){
        product.setQuantity(quantity);
        return product;
    }
}
