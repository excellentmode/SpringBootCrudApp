package org.example.springbootcrudapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.dto.security.AuthRequest;
import org.example.springbootcrudapp.dto.security.AuthResponse;
import org.example.springbootcrudapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        final String token = authService.authenticate(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}