package org.example.springbootcrudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.dto.UserDto;
import org.example.springbootcrudapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Поиск пользователей по фильтрам с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный поиск пользователей",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)))
    })
    @GetMapping("/search")
    public Page<UserDto> search(
            @Parameter(description = "Имя пользователя, поиск по префиксу") @RequestParam(required = false) String name,
            @Parameter(description = "Email пользователя, точное совпадение") @RequestParam(required = false) String email,
            @Parameter(description = "Телефон пользователя, точное совпадение") @RequestParam(required = false) String phone,
            @Parameter(description = "Дата рождения (поиск всех, кто старше чем введённая дата) (yyyy-MM-dd)") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @Parameter(hidden = true) Pageable pageable
    ) {

        return userService.searchUsers(name, email, phone, dateOfBirth, pageable);
    }
}