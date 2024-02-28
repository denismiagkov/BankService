package com.dmiagkov.bank.application.mapper;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.Account;
import com.dmiagkov.bank.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "patronymic", target = "patronymic")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    UserDto userToUserDto(User user);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "patronymic", target = "patronymic")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);
}