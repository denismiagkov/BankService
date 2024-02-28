package com.dmiagkov.bank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {
    /**
     * Уникальный идентификатор транзакции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Номер счета, на котором выполняется транзакция
     */
    private Long accountId;
    /**
     * Дата и время выполнения транзакции
     */
    private LocalDateTime date;
    /**
     * Тип транзакции - дебетовая или кредитная
     *
     * @see TransactionType
     */
    private TransactionType type;
    /**
     * Сумма транзакции
     */
    private BigDecimal amount;
}
