package com.dmiagkov.bank.application.mapper;

import com.dmiagkov.bank.application.dto.outgoing.TransactionDto;
import com.dmiagkov.bank.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "amount", target = "amount")
    TransactionDto transactionToTransactionDto(Transaction transaction);

    @Mapping(source = "accountId", target = "accountId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "amount", target = "amount")
    Transaction transactionToTransactionDto(TransactionDto transactionDto);

    List<TransactionDto> listTransactionsToListTransactionDtos(List<Transaction> transactions);
}
