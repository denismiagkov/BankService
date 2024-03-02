package com.dmiagkov.bank.application.service;

import com.dmiagkov.bank.application.dto.incoming.TransactionApplyDto;
import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.AccountDto;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели счета.
 */
public interface AccountService {

    /**
     * Метод создает денежный счет пользователя.
     *
     * @param userRegisterDto Регистрационные данные пользоветеля
     * @param userId          id пользовтеля
     * @return AccountDto Сведения о счете
     */
    AccountDto createAccount(UserRegisterDto userRegisterDto, Long userId);

    /**
     * * Метод реализует просмотр текущего баланса
     *
     * @param userId id пользователя
     * @return AccountDto Информация о счете пользователя
     */
    AccountDto getBalance(Long userId);

    /**
     * Метод обновляет состояние счета пользоветеля после совешения транзакции
     *
     * @param transactionInfo Сведения о транзакции
     * @return Обновленная информация о счете
     */
    AccountDto setBalance(TransactionApplyDto transactionInfo);
}
