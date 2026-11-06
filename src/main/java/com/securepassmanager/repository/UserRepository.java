package com.securepassmanager.repository;

import com.securepassmanager.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserRepository {

    // Guardar un usuario en SQLite
    public User save(User user) throws SQLException {

        if (user == null ||
                user.getUsername() == null ||
                user.getUsername().isBlank() ||
                user.getEmail() == null ||
                user.getEmail().isBlank() ||
                user.getPasswordHash() == null ||
                user.getPasswordHash().isBlank()) {

            throw new IllegalArgumentException(
                    "Los datos del usuario son inválidos."
            );
        }

        String sql = """
                INSERT INTO users (
                    username,
                    email,
                    password_hash
                )
                VALUES (?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException(
                        "No se pudo guardar el usuario."
                );
            }

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    user.setId(generatedKeys.getInt(1));

                    return user;
                }

                throw new SQLException(
                        "No se pudo obtener el ID del usuario."
                );
            }
        }
    }

    // Buscar usuario por nombre
    public Optional<User> findByUsername(
            String username
    ) throws SQLException {

        String sql = """
                SELECT id,
                       username,
                       email,
                       password_hash
                FROM users
                WHERE username = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return Optional.of(
                            mapResultSetToUser(resultSet)
                    );
                }
            }
        }

        return Optional.empty();
    }

    // Buscar usuario por correo
    public Optional<User> findByEmail(
            String email
    ) throws SQLException {

        String sql = """
                SELECT id,
                       username,
                       email,
                       password_hash
                FROM users
                WHERE email = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return Optional.of(
                            mapResultSetToUser(resultSet)
                    );
                }
            }
        }

        return Optional.empty();
    }

    // Comprobar si existe un nombre de usuario
    public boolean existsByUsername(
            String username
    ) throws SQLException {

        return findByUsername(username).isPresent();
    }

    // Comprobar si existe un correo electrónico
    public boolean existsByEmail(
            String email
    ) throws SQLException {

        return findByEmail(email).isPresent();
    }

    // Convertir una fila de SQLite en un objeto User
    private User mapResultSetToUser(
            ResultSet resultSet
    ) throws SQLException {

        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password_hash")
        );
    }
}