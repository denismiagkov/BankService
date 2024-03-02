package com.dmiagkov.bank.security.service;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.security.http.JwtRequest;
import com.dmiagkov.bank.security.http.JwtResponse;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {
    /**
     * Метод регистрации нового пользователя
     */
    UserDto signUp(UserRegisterDto userRegisterDto);

    /**
     * Метод входа в приложение
     */
    JwtResponse signIn(JwtRequest request);
}
