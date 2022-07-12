package com.letscode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/sales")
public class SalesAdviceController {

    @GetMapping("/health-check")
    public String getSalesAdvice() {
        return "Sales API is up and running.";
    }
}
