package com.dmiagkov.bank.security.service;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.security.http.JwtRequest;
import com.dmiagkov.bank.security.http.JwtResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserDto signUp(UserRegisterDto userRegisterDto);
    JwtResponse signIn(JwtRequest request);
}
