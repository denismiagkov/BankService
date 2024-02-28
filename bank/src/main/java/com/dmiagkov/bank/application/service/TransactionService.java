package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели транзакции.
 */
public interface TransactionService {

    TransactionDto commitTransaction(TransactionDto apply);

    /**
     * Метод должен реализовывать просмотр истории транзакций
     */
    List<TransactionDto> getTransactionHistory(Long accountId);

}
