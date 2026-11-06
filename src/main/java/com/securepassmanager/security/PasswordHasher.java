package com.securepassmanager.security;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {

    private static final int WORK_FACTOR = 12;

    private PasswordHasher() {
    }

    public static String hashPassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña no puede estar vacía."
            );
        }

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(WORK_FACTOR)
        );
    }

    public static boolean verifyPassword(
            String password,
            String storedHash
    ) {

        if (password == null || storedHash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, storedHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}