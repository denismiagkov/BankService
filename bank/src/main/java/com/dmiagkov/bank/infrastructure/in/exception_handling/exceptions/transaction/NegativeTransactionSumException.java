package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.transaction;

public class NegativeTransactionSumException extends RuntimeException {
    public NegativeTransactionSumException() {
        super("Transaction sum must be positive");
    }
}
