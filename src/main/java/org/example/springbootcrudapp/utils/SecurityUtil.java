package org.example.springbootcrudapp.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long userId) {
            return userId;
        }
        throw new IllegalStateException("Пользователь с id: +" + principal + "не найден в текущем контексте");
    }
}