package com.dmiagkov.bank.security.controller;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.aspects.annotations.Loggable;
import com.dmiagkov.bank.infrastructure.in.validator.UserDtoValidator;
import com.dmiagkov.bank.security.service.AuthService;
import com.dmiagkov.bank.security.http.JwtRequest;
import com.dmiagkov.bank.security.http.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Loggable
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserDtoValidator validator;

    @Operation(
            summary = "Adds new telephone number to user progile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Phone is added successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - User violates constraints on data handling")
            })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = authService.signIn(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @Operation(
            summary = "Registers new user in application",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created - User is registered successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - User enters incorrect data")
            })
    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        validator.validateRegisterDto(userRegisterDto);
        UserDto userDto = authService.signUp(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDto);
    }
}