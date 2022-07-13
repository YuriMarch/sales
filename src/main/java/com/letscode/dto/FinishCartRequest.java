package com.letscode.dto;

import lombok.Data;

@Data
public class FinishCartRequest {
    private String cartId;
    private String shopperId;
    private String cpf;
    private String password;
}
