package com.mycompany.gamecloud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ConnectionManager connectionManager = ConnectionManager.getConnectionManagerInstance();

            stage.setOnCloseRequest(event -> {
                connectionManager.closeConnection();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
