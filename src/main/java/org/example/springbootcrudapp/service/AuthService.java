package org.example.springbootcrudapp.service;


import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.dto.security.AuthRequest;
import org.example.springbootcrudapp.model.UserModel;
import org.example.springbootcrudapp.security.JwtUtil;
import org.example.springbootcrudapp.service.data.UserDataService;
import org.example.springbootcrudapp.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserDataService userDataService;
    private final JwtUtil jwtUtil;


    public String authenticate(final AuthRequest request) {
        final UserModel user = userDataService.getByEmail(request.getEmail());

        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return jwtUtil.generateToken(user.getId());
    }
}