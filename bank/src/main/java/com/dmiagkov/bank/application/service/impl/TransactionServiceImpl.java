package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.application.mapper.TransactionMapper;
import com.dmiagkov.bank.application.repository.TransactionRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.TransactionService;
import com.dmiagkov.bank.domain.Transaction;
import com.dmiagkov.bank.infrastructure.in.validator.TransactionDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Низкоуровневый сервис, реализующий методы <strong>по пополнению и списанию денежных средств со счета игрока</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;
    private final TransactionDtoValidator validator;

    @Transactional
    @Override
    public TransactionDto commitCreditTransaction(TransactionApplyDto transactionInfo) {
        AccountDto account = accountService.setBalance(transactionInfo);
        Transaction transactionCredit = createTransaction(transactionInfo, account);
        return transactionMapper.transactionToTransactionDto(transactionCredit);
    }

    @Transactional
    @Override
    public TransactionDto commitDebitTransaction(TransactionApplyDto transactionInfo) {
        AccountDto account = accountService.setBalance(transactionInfo);
        Transaction transactionDebit = createTransaction(transactionInfo, account);
        TransactionApplyDto correspondDto = new TransactionApplyDto(transactionInfo);
        commitCreditTransaction(correspondDto);
        return transactionMapper.transactionToTransactionDto(transactionDebit);
    }

    /**
     * Метод сообщает историю дебетовых и кредитных операций по денежному счету игрока
     *
     * @param playerId идентификатор игрока, об истории транзакций которого запрашивается информация
     * @return список дебетовых и кредитных операций по счету игрока
     */
    @Override
    public List<TransactionDto> getTransactionHistory(Long accountId) {
        List<Transaction> userTransactions = transactionRepository.findAllByAccountId(accountId);
        return transactionMapper.listTransactionsToListTransactionDtos(userTransactions);
    }

    private Transaction createTransaction(TransactionApplyDto transactionInfo, AccountDto account) {
        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .date(LocalDateTime.now())
                .type(transactionInfo.getType())
                .amount(transactionInfo.getAmount())
                .build();
        return transactionRepository.save(transaction);
    }
}

