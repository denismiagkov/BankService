package com.dmiagkov.bank.application.dto.outgoing;

import com.dmiagkov.bank.domain.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Сущность транзакции")
public class TransactionDto {

    @Schema(description = "Идентификатор транзакции")
    private Long id;
//    @Schema(description = "Идентификатор пользователя")
//    private Long userId;
    @Schema(description = "Номер счета")
    private Long accountId;
    @Schema(description = "Время совершения транзакции")
    private LocalDateTime date;
    @Schema(description = "Тип транзакции", examples = {"DEBIT", "CREDIT"})
    private Transaction.TransactionType type;
    @Schema(description = "Сумма транзакции")
    private BigDecimal amount;
}
