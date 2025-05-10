package org.example.springbootcrudapp.service.data;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.entity.AccountData;
import org.example.springbootcrudapp.mapper.AccountMapper;
import org.example.springbootcrudapp.model.AccountModel;
import org.example.springbootcrudapp.repository.AccountRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDataService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public AccountModel getByUserId(Long userId) {

        final AccountData accountData = accountRepository.findByUserDataId(userId)
                .orElseThrow(() -> new RuntimeException("Аккаунт у пользователя с id: " + userId + " не найден"));

        return accountMapper.toAccountModel(accountData);
    }

    public void save(AccountModel model) {
        accountRepository.save(accountMapper.toAccountEntity(model));
    }

    public List<AccountModel> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toAccountModel)
                .toList();
    }
}