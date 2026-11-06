package com.securepassmanager.controller;

import com.securepassmanager.model.User;
import com.securepassmanager.service.UserService;
import com.securepassmanager.util.SceneManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private final UserService userService = new UserService();

    @FXML
    public void register() {

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validar campos obligatorios
        if (username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Campos obligatorios",
                    "Debes completar todos los campos."
            );

            return;
        }

        // Verificar coincidencia de contraseñas
        if (!password.equals(confirmPassword)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Contraseñas diferentes",
                    "Las contraseñas no coinciden."
            );

            passwordField.clear();
            confirmPasswordField.clear();

            return;
        }

        try {

            // Registrar usuario mediante UserService
            User registeredUser = userService.registerUser(
                    username,
                    email,
                    password
            );

            // Confirmar registro exitoso
            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Registro exitoso",
                    "Tu cuenta ha sido creada correctamente."
            );

            // Regresar al inicio de sesión
            Stage stage = (Stage) usernameField
                    .getScene()
                    .getWindow();

            SceneManager.switchScene(
                    stage,
                    "login.fxml"
            );

        } catch (IllegalArgumentException e) {

            // Errores de validación
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error de registro",
                    e.getMessage()
            );

        } catch (SQLException e) {

            // Errores de SQLite
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    "No fue posible registrar el usuario."
            );

            System.err.println(
                    "Error SQL durante el registro."
            );

        } finally {

            // Limpiar contraseñas de los campos visuales
            passwordField.clear();
            confirmPasswordField.clear();
        }
    }

    @FXML
    public void goToLogin(ActionEvent event) {

        Stage stage = (Stage) usernameField
                .getScene()
                .getWindow();

        SceneManager.switchScene(
                stage,
                "login.fxml"
        );
    }

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}