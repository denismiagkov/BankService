package com.dmiagkov.bank.application.dto.outgoing;

import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private LocalDate birthDate;
    private List<Phone> phones;
    private List<Email> email;
    private String login;
}
