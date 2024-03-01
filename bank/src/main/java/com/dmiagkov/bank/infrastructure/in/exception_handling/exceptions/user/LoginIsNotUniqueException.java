package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.user;

public class LoginIsNotUniqueException extends RuntimeException{
    public LoginIsNotUniqueException(String login) {
        super("Логин '" + login + "' не является уникальным");
    }
}
