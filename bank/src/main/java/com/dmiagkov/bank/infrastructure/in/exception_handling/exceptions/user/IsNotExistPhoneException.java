package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.user;

public class IsNotExistPhoneException extends RuntimeException {
    public IsNotExistPhoneException() {
        super("User doesn't have this phone number!");
    }
}
