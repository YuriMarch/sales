package com.letscode.service;

import com.letscode.dto.Product;
import com.letscode.model.Cart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {

    private BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Product> products = cart.getProducts();
        for (Product product : products) {
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return totalPrice;
    }

    public Cart updateTotalPrice(Cart cart) {
        cart.setTotalPrice(calculateTotalPrice(cart));
        return cart;
    }
}
