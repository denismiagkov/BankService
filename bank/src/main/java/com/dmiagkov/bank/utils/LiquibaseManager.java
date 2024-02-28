package com.dmiagkov.bank.utils;

import com.dmiagkov.bank.config.yaml.LiquibaseConfig;
import com.dmiagkov.bank.utils.exceptions.DatabaseConnectionNotEstablishedException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public final class LiquibaseManager {
    private static final String QUERY_CREATE_MIGRATION_SCHEMA = "CREATE SCHEMA IF NOT EXISTS";
    private final LiquibaseConfig config;
    private final ConnectionManager connectionManager;

//    @PostConstruct
//    void init() {
//        System.out.println("CREATE LIQUIBASE SCHEMA");
//        String template = String.join(" ",
//                QUERY_CREATE_MIGRATION_SCHEMA,
//                config.getLiquibaseSchema());
//        try (Connection connection = connectionManager.open();
//             PreparedStatement statement = connection.prepareStatement(template)) {
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DatabaseConnectionNotEstablishedException(e.getMessage());
//        }
//    }
}
