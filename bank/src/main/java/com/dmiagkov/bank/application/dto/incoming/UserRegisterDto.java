package com.dmiagkov.bank.application.dto.incoming;

import com.dmiagkov.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserRegisterDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;
}
