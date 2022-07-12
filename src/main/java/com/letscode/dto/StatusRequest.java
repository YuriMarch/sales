package com.letscode.dto;

import com.letscode.enumerator.Status;
import lombok.Data;

@Data
public class StatusRequest {
    private String cartId;
    private Status statusCart;
}
