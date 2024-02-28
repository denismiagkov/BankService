package com.dmiagkov.bank.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {
    /**
     * Уникальный идентификатор транзакции
     */
    private Long id;
    /**
     * Номер счета, на котором выполняется транзакция
     */
    private Long accountId;
    /**
     * Дата и время выполнения транзакции
     */
    private LocalDateTime time;
    /**
     * Тип транзакции - дебетовая или кредитная
     *
     * @see TransactionType
     */
    private  TransactionType type;
    /**
     * Сумма транзакции
     */
    private BigDecimal amount;
}
