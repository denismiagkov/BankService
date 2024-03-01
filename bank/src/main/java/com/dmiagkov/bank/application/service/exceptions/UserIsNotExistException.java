package com.dmiagkov.bank.application.service.exceptions;

public class UserIsNotExistException extends RuntimeException{
    public UserIsNotExistException() {
        super("User with this property doesn't exist");
    }
}
