package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.service.UserService;
import com.dmiagkov.bank.infrastructure.in.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DtoValidator validator;

    @PostMapping("/api/users/phone")
    public ResponseEntity<UserDto> addPhone(@RequestAttribute Long userId,
                                            @RequestBody UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateAddPhone(updateInfoDto);
        UserDto userDto = userService.addPhone(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @DeleteMapping("/api/users/phone")
    public ResponseEntity<UserDto> deletePhone(@RequestAttribute Long userId,
                                               @RequestBody UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateRemovePhone(updateInfoDto);
        UserDto userDto = userService.deletePhone(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @PostMapping("/api/users/email")
    public ResponseEntity<UserDto> addEmail(@RequestAttribute Long userId,
                                            @RequestBody UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateAddEmail(updateInfoDto);
        UserDto userDto = userService.addEmail(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @DeleteMapping("/api/users/email")
    public ResponseEntity<UserDto> deleteEmail(@RequestAttribute Long userId,
                                               @RequestBody UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateRemoveEmail(updateInfoDto);
        UserDto userDto = userService.deleteEmail(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }
}
