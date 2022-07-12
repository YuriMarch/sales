package com.letscode.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private String size;
    private String category;
}
