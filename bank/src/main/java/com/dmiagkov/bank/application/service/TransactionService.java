package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;

import java.util.List;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели транзакции.
 */
public interface TransactionService {

    /**
     * Метод должен реализовывать просмотр истории транзакций
     */
    List<TransactionDto> getTransactionHistory(Long accountId);

    TransactionDto commitCreditTransaction(TransactionApplyDto transactionInfo);

    TransactionDto commitDebitTransaction(TransactionApplyDto transactionInfo);
}
