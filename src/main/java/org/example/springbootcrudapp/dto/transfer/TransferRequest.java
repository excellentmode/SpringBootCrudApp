package org.example.springbootcrudapp.dto.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    @NotNull
    private Long toUserId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Минимальная сумма перевода 0.01")
    private BigDecimal amount;
}