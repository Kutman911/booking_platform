package com.example.booking_platform.controller;

import com.example.booking_platform.model.dto.LoginRequest;
import com.example.booking_platform.model.dto.RegisterRequest;
import com.example.booking_platform.model.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Пока заглушки
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok("User registered (stub)");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new AuthResponse("fake-jwt-token"));
    }
}
