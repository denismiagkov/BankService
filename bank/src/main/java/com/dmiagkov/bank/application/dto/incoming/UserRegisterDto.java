package com.dmiagkov.bank.application.dto.incoming;

import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @JsonProperty("phones")
    private List<Phone> phones;

    @JsonProperty("email")
    private List<Email> email;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("deposit")
    private BigDecimal initialDeposit;
}
