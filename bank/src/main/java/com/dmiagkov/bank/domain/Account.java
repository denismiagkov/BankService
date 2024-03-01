package com.dmiagkov.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String number;
    @Positive
    private BigDecimal balance;
    private LocalDateTime openedAt;
    private BigDecimal initialDeposit;
//
//    @Builder
//    public Account(Long userId, String number, BigDecimal amount, LocalDateTime openedAt, BigDecimal initialDeposit) {
//        this.userId = userId;
//        this.number = number;
//        this.balance = amount;
//        this.initialDeposit = initialDeposit;
//    }

}
