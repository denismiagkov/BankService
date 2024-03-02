package com.dmiagkov.bank.infrastructure.in.validator;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.service.UserService;
import com.dmiagkov.bank.application.service.exceptions.UserIsNotExistException;
import com.dmiagkov.bank.domain.Transaction;
import com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.transaction.NegativeTransactionSumException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionDtoValidator {
    private final UserService userService;

    /**
     * Валидирует сумму поступления и id получателя платежа
     */
    public boolean validateTransactionInfo(TransactionApplyDto dto) {
        return dto.getType().equals(Transaction.TransactionType.CREDIT)
                ? validateIncomingSum(dto)
                : validateRecipient(dto);
    }

    /**
     * Валидирует сумму поступления
     */
    public boolean validateIncomingSum(TransactionApplyDto dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeTransactionSumException();
        }
        return true;
    }

    /**
     * Валидирует получателя платежа
     */
    public boolean validateRecipient(TransactionApplyDto dto) {
        if (!userService.existsUser(dto.getRecepientId())) {
            throw new UserIsNotExistException();
        }
        return true;
    }
}
