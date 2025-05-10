package org.example.springbootcrudapp.service.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    @CacheEvict(value = "user", key = "#userId")
    public void clearUserCache(Long userId) {
    }
}