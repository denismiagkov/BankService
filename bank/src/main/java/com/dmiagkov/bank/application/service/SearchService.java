package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

/**
 * Сервис api поиска
 */
public interface SearchService {
    /**
     * Поиск пользователя по email
     */
    UserDto findUserByEmail(Email email);

    /**
     * Поиск пользоваетля по номеру телефона
     */
    UserDto findUserByPhone(Phone phone);

    /**
     * Поиск пользователей по возрасту: родившихся после указанной даты
     */
    List<UserDto> findUsersByBirthDate(LocalDate date, PageRequest pageable);


    /**
     * Поиск пользователей по имени и фамилии
     */
    List<UserDto> findUsersByName(String firstName, String lastname, PageRequest pageable);
}
