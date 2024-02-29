package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions;

public class PhoneIsNotUniqueException extends RuntimeException {
    public PhoneIsNotUniqueException() {
        super("This phone number is using by another user!");
    }
}
