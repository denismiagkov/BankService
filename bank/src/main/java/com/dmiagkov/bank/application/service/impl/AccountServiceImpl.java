package com.dmiagkov.bank.application.service.impl;

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
                .amount(userRegisterDto.getBalance())
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

    /**
     * Метод корректирует баланс на счете игрока после пополнения счета
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    public AccountDto setBalance(Transaction transaction) {
        if (areFundsEnough(transaction.getAccountId(), transaction.getAmount())) {
            return accountMapper.accountToAccountDto(
                    accountRepository.update(transaction.getAccountId(), transaction.getAmount()));
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
    private boolean areFundsEnough(Long userId, BigDecimal amount) {
        return getBalance(userId).getBalance().compareTo(amount) < 0;
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
}
