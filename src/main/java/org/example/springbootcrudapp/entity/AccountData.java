package org.example.springbootcrudapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "account_data")
public class AccountData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserData userData;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance;
}