package com.securepassmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.securepassmanager.repository.DatabaseInitializer;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseInitializer.initializeDatabase();

        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/login.fxml")
        );

        Scene scene = new Scene(root, 500, 600);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/style.css")
                        .toExternalForm()
        );

        stage.setTitle("SecurePass Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}