package org.example.springbootcrudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.dto.transfer.TransferRequest;
import org.example.springbootcrudapp.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//todo Сделать обработку ошибок,чтобы они соответствовали кодам указанным в swagger.

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    @Operation(summary = "Перевод средств от авторизованного пользователя к другому")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Перевод выполнен успешно"),
            @ApiResponse(responseCode = "400", description = "Невозможно выполнить перевод")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void transfer(@RequestBody @Valid TransferRequest request) {
        transferService.transfer(request);
    }
}