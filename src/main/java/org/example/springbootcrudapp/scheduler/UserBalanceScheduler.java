package org.example.springbootcrudapp.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootcrudapp.model.AccountModel;
import org.example.springbootcrudapp.service.cache.UserCacheService;
import org.example.springbootcrudapp.service.data.AccountDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserBalanceScheduler {
    private final AccountDataService accountDataService;
    private final UserCacheService userCacheService;

    /**
     * Увеличиваем баланс на 10% до предела в 207% от начального значения (начальное значение храним в User.initialBalance)
     */

    @Scheduled(fixedRate = 30000) // 30 секунд
    @Transactional
    public void increaseBalancePeriodically() {
        List<AccountModel> accounts = accountDataService.getAll();

        for (AccountModel account : accounts) {
            final BigDecimal beforeIncrease = account.getBalance();

            final BigDecimal maxAllowed = account.getInitialBalance().multiply(BigDecimal.valueOf(2.07));
            BigDecimal nextBalance = account.getBalance().multiply(BigDecimal.valueOf(1.10)).setScale(2, RoundingMode.HALF_UP);

            if (nextBalance.compareTo(maxAllowed) > 0) {
                nextBalance = maxAllowed;
            }

            if (account.getBalance().compareTo(nextBalance) < 0) {
                account.setBalance(nextBalance);
                accountDataService.save(account);
                log.info("Баланс для пользователя с id: {} увеличен с {} до {}", account.getUserId(), beforeIncrease, nextBalance);
                userCacheService.clearUserCache(account.getUserId());
            }
        }
    }
}