package com.dmiagkov.bank.application.service.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super("User with this property doesn't exist");
    }
}
