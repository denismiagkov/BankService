package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.service.SearchService;
import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(
            summary = "Finds user by his email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - User with this email exists and successfully returns",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NotFound - User with this email doesn't exist")
            })
    @GetMapping("/api/users/search/email")
    public ResponseEntity<UserDto> findUserByEmail(@RequestParam @Parameter(description = "user email") Email email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchService.findUserByEmail(email));
    }

    @Operation(
            summary = "Finds user by his phone",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - User with this phone exists and successfully returns",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NotFound - User with this phone doesn't exist")
            })
    @GetMapping("/api/users/search/phone")
    public ResponseEntity<UserDto> findUserByPhone(@RequestParam @Parameter(description = "user phone number") Phone phone) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchService.findUserByPhone(phone));
    }

    @Operation(
            summary = "Finds users born after defined date",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Users born after the date exist and successfully return",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserDto.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BadRequest - Invalid input data")
            })
    @GetMapping("/api/users/search/birthday")
    public ResponseEntity<List<UserDto>> findUsersByBirthdate(
            @RequestParam @Parameter(description = "user birth date") LocalDate date,
            @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "parameter of pagination - page") int page,
            @RequestParam(name = "size", defaultValue = "50") @Parameter(description = "parameter of pagination - page size") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchService.findUsersByBirthDate(date, pageable));
    }

    @Operation(
            summary = "Finds users born after defined date",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Users born after the date exist and successfully return",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserDto.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BadRequest - Invalid input data")
            })
    @GetMapping("/api/users/search/name")
    public ResponseEntity<List<UserDto>> findUsersByName(
            @RequestParam(name = "firstname") @Parameter(description = "user first name") String firstName,
            @RequestParam(name = "lastname") @Parameter(description = "user first name") String lastName,
            @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "parameter of pagination - page") int page,
            @RequestParam(name = "size", defaultValue = "50") @Parameter(description = "parameter of pagination - page size") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchService.findUsersByName(firstName, lastName, pageable));
    }


}
