package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDto registerUser(UserRegisterDto userRegisterDto);
    UserDto addPhone(UpdateInfoDto updateDto);
    UserDto getUserDto(String login);
    UserDto deletePhone(UpdateInfoDto updateDto);

    UserDto addEmail(UpdateInfoDto updateInfoDto);

    UserDto deleteEmail(UpdateInfoDto updateInfoDto);
}
