package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions;

public class IsOnlyOneEmailException extends RuntimeException {
    public IsOnlyOneEmailException() {
        super("Removing singular email is not allowed!");
    }
}
