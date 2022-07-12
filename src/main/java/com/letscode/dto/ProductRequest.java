package com.letscode.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String cartId;
    private String shopperId;
    private String productId;
    private Integer quantity;
}
