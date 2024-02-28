package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.domain.Transaction;

import java.math.BigDecimal;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели счета.
 */
public interface AccountService {

    /**
     * Метод должен реализовывать создание счета
     */
    AccountDto createAccount(UserRegisterDto userRegisterDto, Long userId);

    /**
     * * Метод должен реализовывать просмотр текущего баланса
     */
    AccountDto getBalance(Long userId);

    AccountDto setBalance(Transaction transaction);
}
