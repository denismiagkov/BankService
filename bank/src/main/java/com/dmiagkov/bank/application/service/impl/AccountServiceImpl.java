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

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Integer MAX_PERCENT_INCOME = 207;
    private static final Integer PERCENT_RATE = 5;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public AccountDto getBalance(Long userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        return accountMapper.accountToAccountDto(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * Метод обновляет данные о балансе счета с учетом начисления процентов по депозиту
     *
     * @param account Счет пользователя
     */
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
            BigDecimal newDepositValue = currentPercentIncome.compareTo(maxProbablePercentIncome) < 0
                    ? initialDeposit.add(currentPercentIncome)
                    : initialDeposit.add(maxProbablePercentIncome);
            currentDeposit = currentDeposit.compareTo(newDepositValue) > 0
                    ? currentDeposit
                    : newDepositValue;
            account.setBalance(currentDeposit);
        }
    }

    /**
     * Метод имитирует процесс присвоения номера создаваемому счету при регистрации игрока
     * путем использования генератора случайных чисел
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

    /**
     * Метод увеличивает баланс счета при пополнении счета пользователя
     *
     * @param account Счет пользоветеля
     * @param dto     Сведения о транзакции
     * @return BigDecimal новое состояние счета
     */
    private BigDecimal increaseBalance(Account account, TransactionApplyDto dto) {
        return account.getBalance().add(dto.getAmount());
    }

    /**
     * Метод уменьшает баланс счета при списании средств со счета пользователя
     *
     * @param account Счет пользоветеля
     * @param dto     Сведения о транзакции
     * @return BigDecimal новое состояние счета
     */
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
     * @param account Счет пользоваетеля
     * @param amount  сумма списания
     * @return boolean
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     *                                          совершения транзакции
     */
    private boolean areFundsEnough(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }
}
