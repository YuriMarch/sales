package com.letscode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/health-check")
    public String getHealthCheck() {
        return "Sales API is up and running.";
    }
}
