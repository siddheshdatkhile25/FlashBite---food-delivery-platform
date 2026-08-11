package com.flashbite.user.controller;

import static com.flashbite.common.api.ApiConstants.API_PREFIX;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserLoginResponse;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.dto.UserRegisterResponse;
import com.flashbite.user.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(API_PREFIX + "/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account with hashed password and queues verification email/SMS")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @Operation(summary = "Login user", description = "Authenticates user using email or phone and password, returns access and refresh tokens")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
