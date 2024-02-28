package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.application.mapper.TransactionMapper;
import com.dmiagkov.bank.application.repository.TransactionRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.TransactionService;
import com.dmiagkov.bank.domain.Transaction;
import com.dmiagkov.bank.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /**
     * Метод списывает средства с денежного счета игрока (выполняет кредитную транзакцию), при условии,
     * что на счете достаточно денежных средств.
     *
     * @param accountId идентификатор счета игрока, на котором выполняется транзакция
     * @param amount    сумма выполняемой операции
     */
    @Override
    public TransactionDto commitTransaction(TransactionDto apply) {
        Transaction transaction = Transaction.builder()
                .accountId(apply.getAccountId())
                .amount(apply.getAmount())
                .type(apply.getType())
                .build();
        accountService.setBalance(transaction);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToTransactionDto(transaction);
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
}

