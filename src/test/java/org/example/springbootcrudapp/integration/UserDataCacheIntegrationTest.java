package org.example.springbootcrudapp.integration;

import org.example.springbootcrudapp.config.TestContainersConfig;
import org.example.springbootcrudapp.model.UserModel;
import org.example.springbootcrudapp.service.data.UserDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestContainersConfig.class)
public class UserDataCacheIntegrationTest {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void userShouldBeCachedAfterFirstCall() {
        Long userId = 1L;

        // Первый вызов — идём в бд за юзером
        UserModel user1 = userDataService.getById(userId);

        // Вызываем второй раз - берём из кэша
        UserModel user2 = userDataService.getById(userId);

        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
    }
}