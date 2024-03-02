
package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.TransactionService;
import com.dmiagkov.bank.aspects.annotations.Loggable;
import com.dmiagkov.bank.domain.Transaction;
import com.dmiagkov.bank.infrastructure.in.validator.TransactionDtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Loggable
@Tag(name = "Transaction Controller")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TransactionController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionDtoValidator validator;

    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока и возвращает
     * результат обработки запроса пользователю.
     *
     * @param login Session Attribute "Login" HttpServletRequest, содержащий логин игрока
     * @return текущий баланс на счет игрока
     */
    @Operation(
            summary = "Shows current balance on user account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Current account balance",
                            content = @Content(
                                    schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - User has no admin rights for access to requested data")
            })
    @SecurityRequirement(name = "JWT")
    @GetMapping("/balance")
    public ResponseEntity<AccountDto> getCurrentBalance(@RequestAttribute("userId") Long userId) {
        AccountDto accountDto = accountService.getBalance(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }

    @Operation(
            summary = "Adding funds on account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Account replenished successfully",
                            content = @Content(
                                    schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BadRequest - Invalid input data")
            })
    @SecurityRequirement(name = "JWT")
    @PostMapping("/transaction/credit")
    public ResponseEntity<TransactionDto> commitCreditTransaction(
            @RequestAttribute("userId") @Parameter(description = "user id") Long userId,
            @RequestBody @Parameter(description = "transaction info") TransactionApplyDto transactionInfo) {
        validator.validateTransactionInfo(transactionInfo);
        transactionInfo.setUserId(userId);
        TransactionDto transactionResp = transactionService.commitCreditTransaction(transactionInfo);
        return ResponseEntity.ok()
                .body(transactionResp);
    }

    @Operation(
            summary = "Writing off funds on account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Funds written successfully",
                            content = @Content(
                                    schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BadRequest - Invalid input data")
            })
    @SecurityRequirement(name = "JWT")
    @PostMapping("/transaction/debit")
    public ResponseEntity<TransactionDto> commitDebitTransaction(
            @RequestAttribute("userId") @Parameter(description = "user id") Long userId,
            @RequestBody @Parameter(description = "transaction info") TransactionApplyDto transactionInfo) {
        transactionInfo.setType(Transaction.TransactionType.DEBIT);
        validator.validateTransactionInfo(transactionInfo);
        transactionInfo.setUserId(userId);
        TransactionDto transactionResp = transactionService.commitDebitTransaction(transactionInfo);
        return ResponseEntity.ok()
                .body(transactionResp);
    }
}