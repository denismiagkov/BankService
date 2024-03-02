package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.mapper.UserMapper;
import com.dmiagkov.bank.application.repository.UserRepository;
import com.dmiagkov.bank.application.service.SearchService;
import com.dmiagkov.bank.application.service.exceptions.UserIsNotExistException;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto findUserByEmail(Email email) {
        User user = userRepository.findUserByEmailContaining(email);
        Optional<User> userOptional = Optional.ofNullable(user);
        return userMapper.userToUserDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto findUserByPhone(Phone phone) {
        User user = Optional.ofNullable(
                        userRepository.findUserByPhonesContaining(phone))
                .orElseThrow(UserIsNotExistException::new);
        return userMapper.userToUserDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> findUsersByBirthDate(LocalDate date, PageRequest pageable) {
        List<User> users = userRepository.findUsersByBirthDateAfter(date, pageable);
        return userMapper.listUsersToListUsersDto(users);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> findUsersByName(String firstName, String lastname, PageRequest pageable) {
        List<User> users = userRepository.findAllByName(firstName, lastname, pageable);
        return userMapper.listUsersToListUsersDto(users);
    }
}
