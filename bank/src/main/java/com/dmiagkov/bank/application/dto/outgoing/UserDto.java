package com.dmiagkov.bank.application.dto.outgoing;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String login;
}
