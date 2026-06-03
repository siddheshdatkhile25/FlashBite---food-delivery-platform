package com.flashbite.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ServiceInfoController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "service", "user-service",
                "status", "UP",
                "links", Map.of(
                        "health", "/api/v1/health",
                        "actuator", "/actuator/health"
                )
        );
    }

    @GetMapping("/api/v1/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "user-service",
                "status", "UP"
        );
    }
}
