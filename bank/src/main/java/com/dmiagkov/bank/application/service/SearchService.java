package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    UserDto findUserByEmail(Email email);
    UserDto findUserByPhone(Phone phone);
    List<UserDto> findUsersByBirthDate(LocalDate date, PageRequest pageable);
    List<UserDto> findUsersByName(String firstName, String lastname, PageRequest pageable);
}
