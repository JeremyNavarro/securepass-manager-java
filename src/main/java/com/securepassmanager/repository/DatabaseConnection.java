package com.securepassmanager.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

public final class DatabaseConnection {

    private static final Path DATABASE_PATH =
            Path.of(
                    System.getProperty("user.home"),
                    ".securepassmanager",
                    "securepass.db"
            );

    private static final String DATABASE_URL =
            "jdbc:sqlite:" + DATABASE_PATH.toAbsolutePath();

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {

        try {
            Files.createDirectories(DATABASE_PATH.getParent());
        } catch (IOException e) {
            throw new SQLException(
                    "No se pudo crear el directorio de la base de datos.",
                    e
            );
        }

        Connection connection =
                DriverManager.getConnection(DATABASE_URL);

        try {
            connection.createStatement()
                    .execute("PRAGMA foreign_keys = ON");

            return connection;

        } catch (SQLException e) {
            connection.close();
            throw e;
        }
    }
}