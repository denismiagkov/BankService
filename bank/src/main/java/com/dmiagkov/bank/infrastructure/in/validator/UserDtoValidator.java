package com.dmiagkov.bank.infrastructure.in.validator;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.repository.UserRepository;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoValidator {

    private final UserRepository userRepository;

    public void validateRegisterDto(UserRegisterDto dto) {
        validateLogin(dto);
        validatePhone(dto);
        validateEmail(dto);
    }

    public void validateAddPhone(UpdateInfoDto dto) {
        if (!isValidPhone(dto.getPhoneToAdd())) {
            throw new IncorrectInputPhoneNumberException();
        }
        Phone newPhone = new Phone(dto.getPhoneToAdd());
        if (userRepository.existsUserByPhonesContains(newPhone)) {
            throw new PhoneIsNotUniqueException();
        }
    }

    public void validateRemovePhone(UpdateInfoDto dto) {
        if (!isValidPhone(dto.getPhoneToRemove())) {
            throw new IncorrectInputPhoneNumberException();
        }
        User user = userRepository.findUserById(dto.getUserId());
        if (isOnlyOnePhone(user)) {
            throw new IsOnlyOnePhoneException();
        }
        Phone phone = new Phone(dto.getPhoneToRemove());
        if (!user.getPhones().contains(phone)) {
            throw new IsNotExistPhoneException();
        }
    }

    private boolean isOnlyOnePhone(User user) {
        return user.getPhones().size() <= 1;
    }

    private boolean isOnlyOneEmail(User user) {
        return user.getEmail().size() <= 1;
    }

    private boolean isValidPhone(String phone) {
        return phone != null &&
               phone.matches("\\+\\d+");
    }

    public void validateAddEmail(UpdateInfoDto dto) {
        Email newEmail = new Email(dto.getEmailToAdd());
        if (userRepository.existsUserByEmailContains(newEmail)) {
            throw new EmailIsNotUniqueException();
        }
    }

    public void validateRemoveEmail(UpdateInfoDto dto) {
        User user = userRepository.findUserById(dto.getUserId());
        if (isOnlyOneEmail(user)) {
            throw new IsOnlyOneEmailException();
        }
        Email email = new Email(dto.getEmailToRemove());
        if (!user.getEmail().contains(email)) {
            throw new IsNotExistEmailException();
        }
    }

    public void validateLogin(UserRegisterDto registerDto) {
        if (userRepository.existsByLogin(registerDto.getLogin())) {
            throw new LoginIsNotUniqueException(registerDto.getLogin());
        }
    }

    public void validatePhone(UserRegisterDto dto) {
        for (Phone phone : dto.getPhones()) {
            if (!isValidPhone(phone.getPhoneNumber())) {
                throw new IncorrectInputPhoneNumberException();
            }
            if (userRepository.existsUserByPhonesContains(phone)) {
                throw new PhoneIsNotUniqueException();
            }
        }
    }

    public void validateEmail(UserRegisterDto dto) {
        for (Email email : dto.getEmail()) {
            if (userRepository.existsUserByEmailContains(email)) {
                throw new EmailIsNotUniqueException();
            }
        }
    }
}



