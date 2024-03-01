package com.dmiagkov.bank.application.service.impl;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.application.mapper.AccountMapper;
import com.dmiagkov.bank.application.repository.AccountRepository;
import com.dmiagkov.bank.application.service.AccountService;
import com.dmiagkov.bank.application.service.exceptions.NotEnoughFundsOnAccountException;
import com.dmiagkov.bank.domain.Account;
import com.dmiagkov.bank.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Низкоуровневый сервис, реализующий методы, связанные с <strong>созданием денежного счета игрока,
 * просмотром текущего баланса и истории операций по нему</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Integer MAX_PERCENT_INCOME = 207;
    private static final Integer PERCENT_RATE = 5;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    /**
     * Метод создает денежный счет. Применяется высокоуровневым сервисом при создании
     * и регистрации нового игрока {@link MainService#registerPlayer(com.denismiagkov.walletservice.application.dto.PlayerDto)}
     *
     * @param player игрок, для которого создается денежный счет
     */
    @Override
    public AccountDto createAccount(UserRegisterDto userRegisterDto, Long userId) {
        Account account = Account.builder()
                .userId(userId)
                .number(getAccountNumber())
                .balance(userRegisterDto.getInitialDeposit())
                .openedAt(LocalDateTime.now())
                .initialDeposit(userRegisterDto.getInitialDeposit())
                .build();
        userRegisterDto.setAccountId(account.getNumber());
        accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }

    /**
     * Метод возвращает текущий баланс на счете игрока по его id
     *
     * @param playerId id игрока
     * @return денежный счет
     */
    @Override
    public AccountDto getBalance(Long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        return accountMapper.accountToAccountDto(account);
    }

    private void updateDeposit(Account account) {
        BigDecimal currentDeposit = account.getBalance();
        BigDecimal initialDeposit = account.getInitialDeposit();
        BigDecimal maxProbablePercentIncome = initialDeposit
                .multiply(BigDecimal.valueOf(MAX_PERCENT_INCOME))
                .divide(BigDecimal.valueOf(100));
        BigDecimal receivedIncome = currentDeposit.subtract(initialDeposit);
        if (receivedIncome.compareTo(maxProbablePercentIncome) < 0) {
            LocalDateTime now = LocalDateTime.now();
            long minutes = ChronoUnit.MINUTES.between(
                    account.getOpenedAt(), now);
            BigDecimal currentPercentIncome = initialDeposit
                    .multiply(BigDecimal.valueOf(PERCENT_RATE))
                    .multiply(BigDecimal.valueOf(minutes))
                    .divide(BigDecimal.valueOf(100));
            BigDecimal newDepositValue = initialDeposit.add(currentPercentIncome);
            currentDeposit = currentDeposit.compareTo(newDepositValue) > 0
                    ? currentDeposit
                    : newDepositValue;
            account.setBalance(currentDeposit);
        }
    }

    /**
     * Метод корректирует баланс на счете игрока после пополнения счета
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    public AccountDto setBalance(TransactionApplyDto dto) {
        Account account = accountRepository.findAccountByUserId(dto.getUserId());
        updateDeposit(account);
        BigDecimal newBalance = dto.getType().equals(Transaction.TransactionType.CREDIT)
                ? increaseBalance(account, dto)
                : decreaseBalance(account, dto);
        account.setBalance(newBalance);
        account = accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }


    /**
     * Метод имитирует процесс присвоения номера создаваемому счету при регистрации игрока
     * путем использования генератора случайных чисел {@link AccountServiceImpl#createAccount(Player)}
     *
     * @return номер денежного счета
     */
    private String getAccountNumber() {
        while (true) {
            Random n = new Random();
            String number = String.valueOf(n.nextLong(899_000_000_000_000L) + 100_000_000_000_000L);
            if (!accountRepository.existsByNumber(number)) {
                return number;
            }
        }
    }

    private BigDecimal increaseBalance(Account account, TransactionApplyDto dto) {
        return account.getBalance().add(dto.getAmount());
    }

    private BigDecimal decreaseBalance(Account account, TransactionApplyDto dto) {
        if (areFundsEnough(account, dto.getAmount())) {
            return account.getBalance().subtract(dto.getAmount());
        } else {
            throw new NotEnoughFundsOnAccountException();
        }
    }

    /**
     * Метод рассчитывает, достаточно ли денежных средств на счете игрока для их списания.
     *
     * @param playerId id игрока
     * @param amount   сумма списания
     * @return boolean
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     *                                          совершения транзакции
     */
    private boolean areFundsEnough(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }
}
