package org.example.springbootcrudapp.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
}