package com.securepassmanager.service;

import com.securepassmanager.model.User;
import com.securepassmanager.repository.UserRepository;
import com.securepassmanager.security.PasswordHasher;

import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    // ==========================================
    // REGISTRO DE USUARIOS
    // ==========================================

    public User registerUser(
            String username,
            String email,
            String password
    ) throws SQLException {

        // Validar nombre de usuario
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de usuario es obligatorio."
            );
        }

        // Validar correo electrónico
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo electrónico es obligatorio."
            );
        }

        // Validar contraseña
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria."
            );
        }

        if (password.length() < 12) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 12 caracteres."
            );
        }

        // Normalizar nombre de usuario y correo
        username = username.trim();
        email = email.trim();

        // Verificar usuario existente
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "El nombre de usuario ya está registrado."
            );
        }

        // Verificar correo existente
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "El correo electrónico ya está registrado."
            );
        }

        // Generar hash BCrypt
        String passwordHash =
                PasswordHasher.hashPassword(password);

        // Crear usuario
        User user = new User(
                0,
                username,
                email,
                passwordHash
        );

        // Guardar usuario en SQLite
        return userRepository.save(user);
    }

    // ==========================================
    // AUTENTICACIÓN DE USUARIOS
    // ==========================================

    public User authenticateUser(
            String username,
            String password
    ) throws SQLException {

        // Validar credenciales
        if (username == null || username.isBlank()
                || password == null || password.isEmpty()) {

            throw new IllegalArgumentException(
                    "Debes ingresar tu usuario y contraseña."
            );
        }

        // Buscar usuario en SQLite
        User user = userRepository
                .findByUsername(username.trim())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario o contraseña incorrectos."
                        )
                );

        // Verificar contraseña mediante BCrypt
        boolean validPassword =
                PasswordHasher.verifyPassword(
                        password,
                        user.getPasswordHash()
                );

        if (!validPassword) {
            throw new IllegalArgumentException(
                    "Usuario o contraseña incorrectos."
            );
        }

        // Autenticación exitosa
        return user;
    }
}