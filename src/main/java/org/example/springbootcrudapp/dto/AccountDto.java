package org.example.springbootcrudapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private BigDecimal balance;
    private BigDecimal initialBalance;
}