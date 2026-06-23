package com.flightbooking.controller;

import com.flightbooking.dto.RegisterRequest;
import com.flightbooking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flightbooking.dto.LoginRequest;
import com.flightbooking.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok("Registration successful");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}