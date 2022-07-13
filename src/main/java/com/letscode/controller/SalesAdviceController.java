package com.letscode.controller;

import com.letscode.exceptions.BusinessException;
import com.letscode.exceptions.ExceptionMessage;
import com.letscode.exceptions.NotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class SalesAdviceController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionMessage> businessException(BusinessException e) {
        return ResponseEntity.status(500).body(ExceptionMessage.builder()
                .timestamp(Instant.now())
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(500)
                .build());
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<ExceptionMessage> notExistException(NotExistException e) {
        return ResponseEntity.status(404).body(ExceptionMessage.builder()
                .timestamp(Instant.now())
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(404)
                .build());
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ExceptionMessage> webClientException(WebClientException e) {
        return ResponseEntity.status(500).body(ExceptionMessage.builder()
                .timestamp(Instant.now())
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(500)
                .build());
    }
}
