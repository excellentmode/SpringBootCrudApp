package org.example.springbootcrudapp.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.dto.UserDto;
import org.example.springbootcrudapp.mapper.UserMapper;
import org.example.springbootcrudapp.model.UserModel;
import org.example.springbootcrudapp.service.data.UserDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDataService userDataService;
    private final UserMapper userMapper;

    public Page<UserDto> searchUsers(String name, String email, String phone, LocalDate dateOfBirth, Pageable pageable) {
        final Page<UserModel> userModels = userDataService.searchUsers(name, email, phone, dateOfBirth, pageable);

        return userModels.map(userMapper::toUserDtos);
    }
}