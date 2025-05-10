package org.example.springbootcrudapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootcrudapp.dto.transfer.TransferRequest;
import org.example.springbootcrudapp.model.AccountModel;
import org.example.springbootcrudapp.service.cache.UserCacheService;
import org.example.springbootcrudapp.service.data.AccountDataService;
import org.example.springbootcrudapp.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferService {
    private final AccountDataService accountDataService;
    private final UserCacheService userCacheService;

    @Transactional
    public void transfer(TransferRequest request) {
        final Long fromUserId = SecurityUtil.getCurrentUserId();

        if (fromUserId.equals(request.getToUserId())) {
            throw new IllegalArgumentException("Нельзя переводить самому себе");
        }

        AccountModel from = accountDataService.getByUserId(fromUserId);
        AccountModel to = accountDataService.getByUserId(request.getToUserId());

        final BigDecimal amountNeedToTransfer = request.getAmount();
        if (from.getBalance().compareTo(amountNeedToTransfer) < 0) {
            throw new IllegalArgumentException("Недостаточно средств для перевода");
        }

        from.setBalance(from.getBalance().subtract(amountNeedToTransfer));
        to.setBalance(to.getBalance().add(amountNeedToTransfer));

        accountDataService.save(from);
        accountDataService.save(to);

        log.info("Успешный перевод от пользователя с id: {} -> пользователю с id: {}. Переведено средств: {}.", fromUserId, request.getToUserId(), amountNeedToTransfer);
        userCacheService.clearUserCache(fromUserId);
        userCacheService.clearUserCache(to.getUserId());
    }
}