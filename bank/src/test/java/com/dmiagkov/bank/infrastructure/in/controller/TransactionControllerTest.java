package com.dmiagkov.bank.infrastructure.in.controller;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.repository.TransactionRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.TransactionService;
import com.dmiagkov.bank.application.service.exceptions.NotEnoughFundsOnAccountException;
import com.dmiagkov.bank.application.service.exceptions.UserIsNotExistException;
import com.dmiagkov.bank.domain.Transaction;
import com.dmiagkov.bank.infrastructure.in.controller.config.PostgresExtension;
import com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.transaction.NegativeTransactionSumException;
import com.dmiagkov.bank.infrastructure.in.validator.TransactionDtoValidator;
import com.dmiagkov.bank.security.http.JwtRequest;
import com.dmiagkov.bank.security.http.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@ExtendWith(PostgresExtension.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionDtoValidator validator;
    @Autowired
    TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String token;

    @BeforeEach
    void setup() throws Exception {
        JwtRequest loginRequest = new JwtRequest();
        loginRequest.setLogin("user1");
        loginRequest.setPassword("123");
        String jsonRequest = objectMapper.writeValueAsString(loginRequest);
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login")
                        .content(jsonRequest)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        token = jwtResponse.getAccessToken();
    }

    @Test
    @DisplayName("Method successfully provides transaction:" +
                 "- new transaction is added to all transactions list," +
                 "- type and amount of new transaction are equal to these in http-request," +
                 "- account balance increases on transaction sum")
    void commitCreditTransaction_CommitSuccessful() throws Exception {
        TransactionApplyDto transactionInfo = new TransactionApplyDto();
        BigDecimal amount = new BigDecimal(5000);
        transactionInfo.setAmount(amount);
        String jsonRequest = objectMapper.writeValueAsString(transactionInfo);

        BigDecimal accountBalanceBefore = accountService.getBalance(1L).getBalance();
        List<Transaction> trasactionsBefore = transactionRepository.findAll();

        mockMvc.perform(post("/api/users/transaction/credit")
                        .header("Authorization", String.join(" ",
                                "Bearer", token))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.type").value(Transaction.TransactionType.CREDIT.name()),
                        jsonPath("$.amount").value(amount)
                );

        BigDecimal accountBalanceAfter = accountService.getBalance(1L).getBalance();
        List<Transaction> trasactionsAfter = transactionRepository.findAll();
        int countOfNewlyCommittedTransactions = trasactionsAfter.size() - trasactionsBefore.size();
        BigDecimal replenishmentSumOnAccount = accountBalanceAfter.subtract(accountBalanceBefore);

        assertAll(
                () -> assertThat(countOfNewlyCommittedTransactions).isEqualTo(1),
                () -> assertThat(replenishmentSumOnAccount).isEqualTo(amount)
        );
    }

    @Test
    @DisplayName("User enters invalid sum and method trows exception")
    void commitCreditTransaction_ThrowsException() throws Exception {
        TransactionApplyDto transactionInfo = new TransactionApplyDto();
        BigDecimal amount = new BigDecimal(-5000);
        transactionInfo.setAmount(amount);
        String jsonRequest = objectMapper.writeValueAsString(transactionInfo);

        mockMvc.perform(post("/api/users/transaction/credit")
                        .header("Authorization", String.join(" ",
                                "Bearer", token))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> assertThat(result.getResolvedException()
                                instanceof NegativeTransactionSumException));
    }

    @Test
    @DisplayName("Method successfully provides transaction:" +
                 "- two new transactions ('DEBIT' and 'CREDIT') are added to all transactions list," +
                 "- type and amount of debit transaction are equal to these in http-request," +
                 "- account balance of sender decreases on transaction amount," +
                 "- account balance of recipient increases ")
    void commitDebitTransaction_CommitSuccessful() throws Exception {
        TransactionApplyDto transactionInfo = new TransactionApplyDto();
        BigDecimal amount = new BigDecimal(15000);
        transactionInfo.setAmount(amount);
        transactionInfo.setRecepientId(2L);
        String jsonRequest = objectMapper.writeValueAsString(transactionInfo);

        BigDecimal accountSenderBalanceBefore = accountService.getBalance(1L).getBalance();
        BigDecimal accountRecipientBalanceBefore = accountService.getBalance(2L).getBalance();
        List<Transaction> trasactionsBefore = transactionRepository.findAll();

        mockMvc.perform(post("/api/users/transaction/debit")
                        .header("Authorization", String.join(" ",
                                "Bearer", token))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.type").value(Transaction.TransactionType.DEBIT.name()),
                        jsonPath("$.amount").value(amount)
                );

        BigDecimal accountSenderBalanceAfter = accountService.getBalance(1L).getBalance();
        BigDecimal accountRecipientBalanceAfter = accountService.getBalance(2L).getBalance();
        List<Transaction> trasactionsAfter = transactionRepository.findAll();
        int countOfNewlyCommittedTransactions = trasactionsAfter.size() - trasactionsBefore.size();
        BigDecimal decreaseSumOnSenderAccount = accountSenderBalanceBefore.subtract(accountSenderBalanceAfter);

        assertAll(
                () -> assertThat(countOfNewlyCommittedTransactions).isEqualTo(2),
                () -> assertThat(decreaseSumOnSenderAccount).isEqualTo(amount),
                () -> assertThat(accountRecipientBalanceBefore).isLessThan(accountRecipientBalanceAfter)
        );
    }

    @Test
    @DisplayName("Method throws exception for user to make transaction doesn't have enough money on account")
    void commitDebitTransaction_ThrowsException() throws Exception {
        TransactionApplyDto transactionInfo = new TransactionApplyDto();
        BigDecimal amount = new BigDecimal(150000);
        transactionInfo.setAmount(amount);
        transactionInfo.setRecepientId(2L);
        String jsonRequest = objectMapper.writeValueAsString(transactionInfo);

        mockMvc.perform(post("/api/users/transaction/debit")
                        .header("Authorization", String.join(" ",
                                "Bearer", token))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> assertThat(result.getResolvedException()
                                instanceof NotEnoughFundsOnAccountException));
    }

    @Test
    @DisplayName("Method throws exception for user-recipient doesn't exist (wrong user id indicated)")
    void commitDebitTransaction_ThrowsAnotherException() throws Exception {
        TransactionApplyDto transactionInfo = new TransactionApplyDto();
        BigDecimal amount = new BigDecimal(500);
        transactionInfo.setAmount(amount);
        transactionInfo.setRecepientId(20L);
        String jsonRequest = objectMapper.writeValueAsString(transactionInfo);

        mockMvc.perform(post("/api/users/transaction/debit")
                        .header("Authorization", String.join(" ",
                                "Bearer", token))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isNotFound(),
                        result -> assertThat(result.getResolvedException()
                                instanceof UserIsNotExistException));
    }
}