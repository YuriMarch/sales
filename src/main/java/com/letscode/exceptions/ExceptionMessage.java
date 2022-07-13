package com.letscode.exceptions;

import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionMessage {
    private Instant timestamp;
    private String message;
    private String error;
    private Integer status;
}
