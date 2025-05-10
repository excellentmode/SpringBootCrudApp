package org.example.springbootcrudapp.unit;

import org.example.springbootcrudapp.dto.transfer.TransferRequest;
import org.example.springbootcrudapp.model.AccountModel;
import org.example.springbootcrudapp.service.TransferService;
import org.example.springbootcrudapp.service.cache.UserCacheService;
import org.example.springbootcrudapp.service.data.AccountDataService;
import org.example.springbootcrudapp.utils.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private AccountDataService accountDataService;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        accountDataService = mock(AccountDataService.class);
        UserCacheService userCacheService = mock(UserCacheService.class);
        transferService = new TransferService(accountDataService, userCacheService);
    }

    @Test
    void transferSuccessTest() {
        try (MockedStatic<SecurityUtil> mocked = mockStatic(SecurityUtil.class)) {
            Long fromId = 1L;
            Long toId = 2L;

            mocked.when(SecurityUtil::getCurrentUserId).thenReturn(fromId);

            AccountModel from = new AccountModel();
            from.setUserId(fromId);
            from.setBalance(new BigDecimal("100.00"));

            AccountModel to = new AccountModel();
            to.setUserId(toId);
            to.setBalance(new BigDecimal("20.00"));

            when(accountDataService.getByUserId(fromId)).thenReturn(from);
            when(accountDataService.getByUserId(toId)).thenReturn(to);

            TransferRequest request = new TransferRequest();
            request.setToUserId(toId);
            request.setAmount(new BigDecimal("50.00"));

            transferService.transfer(request);

            assertEquals(new BigDecimal("50.00"), from.getBalance());
            assertEquals(new BigDecimal("70.00"), to.getBalance());
            verify(accountDataService).save(from);
            verify(accountDataService).save(to);
        }
    }

    @Test
    void transferBySelfThrowTest() {
        try (MockedStatic<SecurityUtil> mocked = mockStatic(SecurityUtil.class)) {
            Long userId = 1L;
            mocked.when(SecurityUtil::getCurrentUserId).thenReturn(userId);

            TransferRequest request = new TransferRequest();
            request.setToUserId(userId);
            request.setAmount(new BigDecimal("10.00"));

            assertThrows(IllegalArgumentException.class, () -> transferService.transfer(request));
        }
    }

    @Test
    void transferNotEnoughBalanceThrowTest() {
        try (MockedStatic<SecurityUtil> mocked = mockStatic(SecurityUtil.class)) {
            Long fromId = 1L;
            Long toId = 2L;
            mocked.when(SecurityUtil::getCurrentUserId).thenReturn(fromId);

            AccountModel from = new AccountModel();
            from.setUserId(fromId);
            from.setBalance(new BigDecimal("20.00"));

            when(accountDataService.getByUserId(fromId)).thenReturn(from);

            TransferRequest request = new TransferRequest();
            request.setToUserId(toId);
            request.setAmount(new BigDecimal("50.00"));

            assertThrows(IllegalArgumentException.class, () -> transferService.transfer(request));
        }
    }

    @Test
    void transferTargetUserNotFoundThrowTest() {
        try (MockedStatic<SecurityUtil> mocked = mockStatic(SecurityUtil.class)) {
            Long fromId = 1L;
            Long toId = 999L;

            mocked.when(SecurityUtil::getCurrentUserId).thenReturn(fromId);

            AccountModel from = new AccountModel();
            from.setUserId(fromId);
            from.setBalance(new BigDecimal("100.00"));

            when(accountDataService.getByUserId(fromId)).thenReturn(from);
            when(accountDataService.getByUserId(toId)).thenThrow(new RuntimeException("Пользователь не найден"));

            TransferRequest request = new TransferRequest();
            request.setToUserId(toId);
            request.setAmount(new BigDecimal("10.00"));

            assertThrows(RuntimeException.class, () -> transferService.transfer(request));
        }
    }

    @Test
    void transferSenderNotFoundThrowTest() {
        try (MockedStatic<SecurityUtil> mocked = mockStatic(SecurityUtil.class)) {
            Long fromId = 1L;
            Long toId = 2L;

            mocked.when(SecurityUtil::getCurrentUserId).thenReturn(fromId);

            when(accountDataService.getByUserId(fromId)).thenThrow(new RuntimeException("Пользователь не найден"));

            TransferRequest request = new TransferRequest();
            request.setToUserId(toId);
            request.setAmount(new BigDecimal("10.00"));

            assertThrows(RuntimeException.class, () -> transferService.transfer(request));
        }
    }
}