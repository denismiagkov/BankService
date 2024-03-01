package com.dmiagkov.bank.config;

import com.dmiagkov.bank.application.mapper.AccountMapper;
import com.dmiagkov.bank.application.mapper.TransactionMapper;
import com.dmiagkov.bank.application.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class WebConfig {

    @Bean
    UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    AccountMapper accountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }

    @Bean
    TransactionMapper transactionMapper() {
        return Mappers.getMapper(TransactionMapper.class);
    }

}
