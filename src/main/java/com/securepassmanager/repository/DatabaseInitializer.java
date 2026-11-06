package com.securepassmanager.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initializeDatabase() throws SQLException {

        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    email TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
                );
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             Statement statement =
                     connection.createStatement()) {

            statement.execute(createUsersTable);

            System.out.println(
                    "SecurePass: base de datos inicializada correctamente."
            );
        }
    }
}