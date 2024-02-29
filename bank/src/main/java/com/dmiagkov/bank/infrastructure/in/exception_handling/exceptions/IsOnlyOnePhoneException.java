package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions;

public class IsOnlyOnePhoneException extends RuntimeException {
    public IsOnlyOnePhoneException() {
        super("Removing singular phone is not allowed!");
    }
}
