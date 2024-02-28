
package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс обрабатывает запросы, полученные от пользователя и управляет взаимодействием между внешним
 * и внутренними слоями приложения
 */
@RestController
@RequestMapping("/api")
@Tag(name = "REST-контроллер")
@RequiredArgsConstructor
public class TransactionController {
    private final AccountService accountService;

    private final TransactionService transactionService;

    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока и возвращает
     * результат обработки запроса пользователю.
     *
     * @param login Session Attribute "Login" HttpServletRequest, содержащий логин игрока
     * @return текущий баланс на счет игрока
     */
    @GetMapping("/users/balance")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр баланса", description = "Позволяет узнать текущий баланс на счете игрока")
    public ResponseEntity<AccountDto> getCurrentBalance(@RequestAttribute("userId") Long userId) {
        AccountDto accountDto = accountService.getBalance(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }

    /**
     * Метод вызывает в сервисе историю дебетовых и кредитных операций по счету игрока
     * и возвращает пользователю результат обработки запроса.
     *
     * @param login Session Attribute "Login" HttpServletRequest, содержащий логин игрока
     * @return история транзакций на счете игрока
     */
    @PostMapping("/users/transactions")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "История транзакций", description = "Позволяет просмотреть историю транзакций на счете игрока")
    public ResponseEntity<List<TransactionDto>> getTransactionsHistory(@SessionAttribute("Login") Long accountId) {
        List<TransactionDto> transactionDtoList = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionDtoList);
    }

    /**
     * Метод вызывает в сервисе метод по пополнению денежного счета игрока
     * и возвращает пользователю результат обработки запроса
     *
     * @param login   Session Attribute "Login" HttpServletRequest, содержащий логин игрока
     * @param wrapper класс-обертка для получения значения типа BigDecimal из http-запроса
     * @return статус исполнения запроса
     */
    @PostMapping("/users/depositing")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Пополнение счета", description = "Позволяет пополнить счет игрока")
    public ResponseEntity<TransactionDto> commitTransaction(@SessionAttribute("Login") String login,
                                                            @RequestBody TransactionDto transactionDto) {
        //  BigDecimal amount = wrapper.getAmount();
        final var transactionResp = transactionService.commitTransaction(transactionDto);
        // message.setInfo("Ваш баланс пополнен на сумму " + amount + " " + " денежных единиц");
        return new ResponseEntity<>(transactionResp, HttpStatus.OK);
    }
}