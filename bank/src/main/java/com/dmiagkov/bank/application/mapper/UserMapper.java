package com.dmiagkov.bank.application.mapper;

import com.dmiagkov.bank.application.dto.incoming.EmailDto;
import com.dmiagkov.bank.application.dto.incoming.PhoneDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "patronymic", target = "patronymic")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "phones", target = "phones")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    UserDto userToUserDto(User user);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "patronymic", target = "patronymic")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "phones", target = "phones")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);

    List<UserDto> listUsersToListUsersDto(List<User> users);

    @Mapping(source = "phoneNumber", target = "phoneNumber")
    Phone phoneDtoToPhone(PhoneDto phoneDto);

    @Mapping(source = "phoneNumber", target = "phoneNumber")
    PhoneDto phoneToPhoneDto(Phone phone);

    @Mapping(source = "emailAddress", target = "emailAddress")
    Email emailDtoToEmail(Email e);

    @Mapping(source = "emailAddress", target = "emailAddress")
    EmailDto emailToEmailDto(Email phone);
}