package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.incoming.UpdateInfoDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.service.UserService;
import com.dmiagkov.bank.infrastructure.in.validator.DtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DtoValidator validator;

    @Operation(
            summary = "Adds new telephone number to user profile",
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
    @PostMapping("/api/users/phone")
    public ResponseEntity<UserDto> addPhone(@RequestAttribute Long userId,
                                            @RequestBody @Parameter(description = "new phone number") UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateAddPhone(updateInfoDto);
        UserDto userDto = userService.addPhone(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @Operation(
            summary = "Removes telephone number from user profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Phone is deleted successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - User violates constraints on data handling")
            })
    @DeleteMapping("/api/users/phone")
    public ResponseEntity<UserDto> deletePhone(@RequestAttribute Long userId,
                                               @RequestBody @Parameter(description = "phone number to be deleted") UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateRemovePhone(updateInfoDto);
        UserDto userDto = userService.deletePhone(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @Operation(
            summary = "Adds new email to user profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Email is added successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - User violates constraints on data handling")
            })
    @PostMapping("/api/users/email")
    public ResponseEntity<UserDto> addEmail(@RequestAttribute Long userId,
                                            @RequestBody @Parameter(description = "new email") UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateAddEmail(updateInfoDto);
        UserDto userDto = userService.addEmail(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @Operation(
            summary = "Deletes email from user profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Phone is deleted successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - User violates constraints on data handling")
            })
    @DeleteMapping("/api/users/email")
    public ResponseEntity<UserDto> deleteEmail(@RequestAttribute Long userId,
                                               @RequestBody @Parameter(description = "email to be deleted") UpdateInfoDto updateInfoDto) {
        updateInfoDto.setUserId(userId);
        validator.validateRemoveEmail(updateInfoDto);
        UserDto userDto = userService.deleteEmail(updateInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }
}
