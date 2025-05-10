package org.example.springbootcrudapp.service.data;

import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.entity.UserData;
import org.example.springbootcrudapp.mapper.UserMapper;
import org.example.springbootcrudapp.model.UserModel;
import org.example.springbootcrudapp.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserModel getByEmail(String email) {
        final UserData userData = userRepository.findByEmailsEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь c email: " + email + " не найден"));

        return userMapper.toUserModelAuth(userData);
    }

    @Cacheable("user")
    @Transactional(readOnly = true)
    public UserModel getById(Long userId) {
        final UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь c id: " + userId + " не найден"));

        return userMapper.toUserModel(userData);
    }

    public Page<UserModel> searchUsers(String name, String email, String phone, LocalDate dateOfBirth, Pageable pageable) {
        final Specification<UserData> spec = Specification.where(UserSpecification.hasNameLike(name))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasPhone(phone))
                .and(UserSpecification.bornAfter(dateOfBirth));

        final Page<UserData> userPage = userRepository.findAll(spec, pageable);
        return userPage.map(userMapper::toUserModel);
    }
}