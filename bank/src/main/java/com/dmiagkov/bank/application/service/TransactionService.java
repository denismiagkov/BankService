package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;

import java.util.List;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели транзакции.
 */
public interface TransactionService {

    /**
     * Метод, реализующий пополнение денежного счета пользователя (кредитная транзакция)
     */
    TransactionDto commitCreditTransaction(TransactionApplyDto transactionInfo);

    /**
     * Метод, реализующий списание денежных средств со счета пользователя (деебтовая транзакция)
     */
    TransactionDto commitDebitTransaction(TransactionApplyDto transactionInfo);
}
