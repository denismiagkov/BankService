package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.mapper.UserMapper;
import com.dmiagkov.bank.application.repository.UserRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.UserService;
import com.dmiagkov.bank.application.service.exceptions.LoginIsNotUniqueException;
import com.dmiagkov.bank.application.service.exceptions.PlayerAlreadyExistsException;
import com.dmiagkov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new PlayerAlreadyExistsException(userRegisterDto);
        } else if (userRepository.existsByLogin(userRegisterDto.getLogin())) {
            throw new LoginIsNotUniqueException(userRegisterDto.getLogin());
        } else {
            User toRegisterUser = userMapper.userRegisterDtoToUser(userRegisterDto);
            User registeredUser = userRepository.save(toRegisterUser);
            accountService.createAccount(userRegisterDto, registeredUser.getId());
            return userMapper.userToUserDto(registeredUser);
        }
    }

    public UserDto getUserDto(String login) {
        User user = userRepository.findUserByLogin(login);
        return userMapper.userToUserDto(user);

    }
}
