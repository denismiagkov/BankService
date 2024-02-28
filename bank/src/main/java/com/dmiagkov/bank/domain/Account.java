package com.dmiagkov.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String number;
    @Positive
    private BigDecimal balance;

    @Builder
    public Account(Long userId, String number, BigDecimal amount) {
        this.userId = userId;
        this.number = number;
        this.balance = amount;
    }

}
