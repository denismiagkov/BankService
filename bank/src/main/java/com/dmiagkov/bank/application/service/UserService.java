package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    /**
     * Метод регистрации нового пользоваетля
     */
    UserDto registerUser(UserRegisterDto userRegisterDto);

    /**
     * Метод добавления нового номера телефона
     */
    UserDto addPhone(UpdateInfoDto updateDto);

    /**
     * Метод получения информации о пользователе
     */
    UserDto getUserDto(String login);

    /**
     * Метод удаления номера телефона пользователя
     */
    UserDto deletePhone(UpdateInfoDto updateDto);

    /**
     * Метод добавления нового адреса электронной почты
     */
    UserDto addEmail(UpdateInfoDto updateInfoDto);

    /**
     * Метод удаления адреса электронной почты
     */
    UserDto deleteEmail(UpdateInfoDto updateInfoDto);

    /**
     * Метод проверки существования пользователя
     */
    boolean existsUser(Long id);
}
