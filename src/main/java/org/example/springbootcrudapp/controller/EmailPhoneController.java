package org.example.springbootcrudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.service.EmailPhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//todo Сделать обработку ошибок,чтобы они соответствовали кодам указанным в swagger. Пока отдаём 500-код

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email_phone")
@Tag(name = "Email и Телефоны", description = "Методы для управления информацией пользователя")
public class EmailPhoneController {

    private final EmailPhoneService emailPhoneService;

    @Operation(summary = "Добавить себе email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email добавлен успешно"),
            @ApiResponse(responseCode = "400", description = "Email уже занят")
    })
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void addEmail(
            @RequestParam @Email String email) {
        emailPhoneService.addEmail(email);
    }

    @Operation(summary = "Удалить свой email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email удалён успешно"),
            @ApiResponse(responseCode = "403", description = "Ошибка.Нельзя удалить чужой email")
    })
    @DeleteMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmail(@RequestParam @Email String email) {
        emailPhoneService.deleteEmail(email);
    }

    @Operation(summary = "Добавить себе телефон")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Телефон добавлен успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка. Телефон уже занят")
    })
    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    public void addPhone(@RequestParam @Pattern(regexp = "79\\d{9}") String phone) {
        emailPhoneService.addPhone(phone);
    }

    @Operation(summary = "Удалить свой телефон")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Телефон удалён успешно"),
            @ApiResponse(responseCode = "403", description = "Ошибка. Нельзя удалить чужой телефон")
    })
    @DeleteMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    public void deletePhone(@RequestParam @Pattern(regexp = "79\\d{9}") String phone) {
        emailPhoneService.deletePhone(phone);
    }

    @PutMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить свой номер телефона")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Телефон успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка. Новый телефон уже занят"),
            @ApiResponse(responseCode = "403", description = "Ошибка. Нельзя обновить чужой телефон")
    })
    public void updatePhone(@RequestParam @Pattern(regexp = "79\\d{9}") String oldPhone,
                            @RequestParam @Pattern(regexp = "79\\d{9}") String newPhone) {
        emailPhoneService.updatePhone(oldPhone, newPhone);
    }

    @PutMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить свой email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка. Новый email уже занят"),
            @ApiResponse(responseCode = "403", description = "Ошибка. Нельзя обновить чужой email")
    })
    public void updateEmail(@RequestParam @Email String oldEmail,
                            @RequestParam @Email String newEmail) {
        emailPhoneService.updateEmail(oldEmail, newEmail);
    }
}