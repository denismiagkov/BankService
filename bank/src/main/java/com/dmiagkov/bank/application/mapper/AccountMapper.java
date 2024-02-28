package com.dmiagkov.bank.application.mapper;

import com.dmiagkov.bank.application.dto.outgoing.AccountDto;
import com.dmiagkov.bank.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "balance", target = "balance")
    AccountDto accountToAccountDto(Account account);
}
