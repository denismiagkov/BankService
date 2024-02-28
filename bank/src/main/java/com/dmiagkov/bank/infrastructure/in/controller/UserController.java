package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello_get() {
        return "Hello World!";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String hello_post() {
        return "hello world";
    }

}
