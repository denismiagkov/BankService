package com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions;

public class IncorrectInputPhoneNumberException extends RuntimeException{
    public IncorrectInputPhoneNumberException() {
        super("Номер телефона может содержать только цифры и символ '+'!");
    }
}
