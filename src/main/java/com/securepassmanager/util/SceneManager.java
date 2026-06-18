package com.securepassmanager.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    public static void switchScene(Stage stage, String fxmlFile) {

        try {

            Parent root = FXMLLoader.load(
                    SceneManager.class.getResource("/fxml/" + fxmlFile)
            );

            Scene scene = new Scene(root, 500, 600);

            scene.getStylesheets().add(
                    SceneManager.class
                            .getResource("/css/style.css")
                            .toExternalForm()
            );

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}