package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.user;

public class IsNotExistEmailException extends RuntimeException {
    public IsNotExistEmailException() {
        super("User doesn't have this email!");
    }
}
