package com.dmiagkov.bank.domain;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class Account {
    Long id;
    String number;
    @Positive
    BigDecimal balance;

}
