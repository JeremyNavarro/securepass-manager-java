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

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserService userService = new UserService();

    @FXML
    public void login() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validar campos obligatorios
        if (username.isEmpty() || password.isEmpty()) {

            showError(
                    "Campos obligatorios",
                    "Debes ingresar tu usuario y contraseña."
            );

            passwordField.clear();
            return;
        }

        try {

            // Autenticar usuario mediante SQLite y BCrypt
            User authenticatedUser =
                    userService.authenticateUser(
                            username,
                            password
                    );

            // Verificar autenticación exitosa
            if (authenticatedUser != null) {

                Stage stage = (Stage) usernameField
                        .getScene()
                        .getWindow();

                // Abrir dashboard únicamente si el usuario
                // fue autenticado correctamente
                SceneManager.switchScene(
                        stage,
                        "dashboard.fxml"
                );
            }

        } catch (IllegalArgumentException e) {

            // Credenciales incorrectas
            showError(
                    "Error de autenticación",
                    e.getMessage()
            );

        } catch (SQLException e) {

            // Error de conexión o consulta SQLite
            showError(
                    "Error de base de datos",
                    "No fue posible verificar las credenciales."
            );

            // No registrar contraseñas ni información sensible
            System.err.println(
                    "Error SQL durante la autenticación."
            );

        } finally {

            // Eliminar contraseña del campo visual
            passwordField.clear();
        }
    }

    @FXML
    public void openRegister(ActionEvent event) {

        Stage stage = (Stage) usernameField
                .getScene()
                .getWindow();

        SceneManager.switchScene(
                stage,
                "register.fxml"
        );
    }

    private void showError(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}