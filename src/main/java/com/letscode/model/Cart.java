package com.letscode.model;

import com.letscode.dto.Cep;
import com.letscode.dto.Product;
import com.letscode.enumerator.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
public class Cart {

    @Id
    private String id = UUID.randomUUID().toString();
    private String shopperId;
    private BigDecimal totalPrice;
    private Cep cep;
    private Status status;
    private List<Product> products = new ArrayList<>();
    private LocalDateTime purchaseDate;

}
