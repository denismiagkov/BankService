package com.dmiagkov.bank.application.service.exceptions;

public class NotEnoughFundsOnAccountException extends RuntimeException{
    public NotEnoughFundsOnAccountException() {
        super("Недостаточно денежных средств на счете для совершения транзакции");
    }
}
