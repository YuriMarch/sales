package com.letscode.service;

import com.letscode.exceptions.NotExistException;
import com.letscode.dto.Product;
import com.letscode.dto.ProductRequest;
import com.letscode.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemoveProductService {

    private final SaleRepositoryService saleRepositoryService;
    private final PriceService priceService;

    public Mono<Cart> execute(ProductRequest productRequest){
        return saleRepositoryService.getCartById(productRequest.getCartId())
                .map(cart -> saleRepositoryService.verifyShopper(cart, productRequest.getShopperId()))
                .map(cart -> this.removeProduct(cart, productRequest))
                .map(priceService::updateTotalPrice)
                .flatMap(saleRepositoryService::saveCart);
    }

    private Cart removeProduct(Cart cart, ProductRequest productRequest) {
        List<Product> products = cart.getProducts();
        List<String> productsId = products.stream().map(Product::getId).collect(Collectors.toList());
        saleRepositoryService.validateStatusCart(cart);

        if (!productsId.contains(productRequest.getProductId())) {
            throw new NotExistException("Product does not exist");
        }

        int index = productsId.indexOf(productRequest.getProductId());
        Product prod = products.get(index);
        int newQuantity = prod.getQuantity() - productRequest.getQuantity();
        if (newQuantity <= 0) {
            products.remove(index);
        } else {
            prod.setQuantity(newQuantity);
            products.set(index, prod);
        }

        cart.setProducts(products);
        return cart;
    }


}
