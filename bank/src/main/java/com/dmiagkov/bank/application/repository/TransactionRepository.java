package com.dmiagkov.bank.application.repository;

import com.dmiagkov.bank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Override
    Transaction save(Transaction transaction);

    List<Transaction> findAllByAccountId(Long accountId);
}
