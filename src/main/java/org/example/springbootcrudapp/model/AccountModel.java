package org.example.springbootcrudapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private BigDecimal initialBalance;
}