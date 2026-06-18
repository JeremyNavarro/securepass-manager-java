package com.securepassmanager.controller;

import com.securepassmanager.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void register() {

        System.out.println("Registrando usuario...");
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
}