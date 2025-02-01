package com.example.ratelimiting.controller;

import com.example.ratelimiting.service.RateLimitingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiting")
public class RateLimitingController {

    private final RateLimitingService rateLimitingService;

    public RateLimitingController(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @GetMapping("/resource")
    public ResponseEntity<String> getResource(@RequestHeader("X-API-Key") String apiKey) {
//        String apiKey = "test-api-key"; // Retrieve API key from request headers or JWT token
        if (rateLimitingService.allowRequest(apiKey)) {
            return ResponseEntity.ok( "Resources accessed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded. Try again later.") ;
        }
    }
}
