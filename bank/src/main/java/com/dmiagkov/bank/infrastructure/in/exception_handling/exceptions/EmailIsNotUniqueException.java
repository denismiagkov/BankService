package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions;

public class EmailIsNotUniqueException extends RuntimeException {
    public EmailIsNotUniqueException() {
        super("This email is using by another user!");
    }
}
