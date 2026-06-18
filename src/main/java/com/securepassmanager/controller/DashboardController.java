package com.securepassmanager.controller;

import com.securepassmanager.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private void logout() {
        Stage stage = (Stage) Stage.getWindows().get(0);
        SceneManager.switchScene(stage, "login.fxml");
    }
}