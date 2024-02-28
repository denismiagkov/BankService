package com.dmiagkov.bank.application.repository;

import com.dmiagkov.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Override
    Account save(Account account);

    Account findAccountByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("update Account a " +
           "set a.balance = :amount " +
           "where a.id = :id")
    Account update(Long id, BigDecimal amount);

    boolean existsByNumber(String number);
}
