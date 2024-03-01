package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.mapper.UserMapper;
import com.dmiagkov.bank.application.repository.UserRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.UserService;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        User toRegisterUser = userMapper.userRegisterDtoToUser(userRegisterDto);
        User registeredUser = userRepository.save(toRegisterUser);
        accountService.createAccount(userRegisterDto, registeredUser.getId());
        return userMapper.userToUserDto(registeredUser);
    }

    public UserDto getUserDto(String login) {
        User user = userRepository.findUserByLogin(login);
        return userMapper.userToUserDto(user);
    }

    public UserDto addPhone(UpdateInfoDto updateDto) {
        User user = userRepository.findUserById(updateDto.getUserId());
        user.getPhones().add(
                new Phone(updateDto.getPhoneToAdd())
        );
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public UserDto deletePhone(UpdateInfoDto updateDto) {
        User user = userRepository.findUserById(updateDto.getUserId());
        user.getPhones().remove(
                new Phone(updateDto.getPhoneToRemove())
        );
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public UserDto addEmail(UpdateInfoDto updateDto) {
        User user = userRepository.findUserById(updateDto.getUserId());
        user.getEmail().add(
                new Email(updateDto.getEmailToAdd())
        );
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public UserDto deleteEmail(UpdateInfoDto updateDto) {
        User user = userRepository.findUserById(updateDto.getUserId());
        user.getEmail().remove(
                new Email(updateDto.getEmailToRemove())
        );
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public boolean existsUser(Long id){
        return userRepository.existsById(id);
    }
}
