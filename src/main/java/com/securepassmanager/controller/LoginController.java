package com.securepassmanager.controller;

import com.securepassmanager.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Usuario: " + username);
        System.out.println("Password: " + password);

        Stage stage = (Stage) usernameField.getScene().getWindow();
        SceneManager.switchScene(stage, "dashboard.fxml");
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
}