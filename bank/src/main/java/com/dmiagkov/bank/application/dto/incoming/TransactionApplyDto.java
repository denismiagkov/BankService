package com.dmiagkov.bank.application.dto.incoming;

import com.dmiagkov.bank.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionApplyDto {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("type")
    private Transaction.TransactionType type = Transaction.TransactionType.CREDIT;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("recipient_id")
    private Long recepientId;

    public TransactionApplyDto(TransactionApplyDto transactionInfo) {
        this.userId = transactionInfo.getRecepientId();
        this.amount = transactionInfo.getAmount();
    }
}
