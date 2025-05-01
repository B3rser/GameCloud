/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamecloud;

import java.io.IOException;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author uriel
 */
public class Scene1Controller {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passTextField;
    @FXML
    private Button logBnt;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ConnectionManager connectionManager;

    public void initialize() {
        logBnt.setDisable(true);

        ConnectionManager.init("localhost", 2555);
        connectionManager = ConnectionManager.getConnectionManagerInstance();
        Thread connectionThread = new Thread(() -> {
            connectionManager.run();
            Platform.runLater(() -> {
                if (connectionManager.getIsConnected()) {
                    logBnt.setDisable(false);
                } else {
                    showRetryDialog();
                }
            });
        });
        connectionThread.start();
    }

    private void showRetryDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Error de Conexión");
        dialog.setHeaderText("No se pudo conectar al servidor.");
        Label content = new Label("Por favor, revisa tu conexión de red.");

        ButtonType retryButtonType = new ButtonType("Reintentar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().setContent(new VBox(10, content));
        dialog.getDialogPane().getButtonTypes().addAll(retryButtonType, cancelButtonType);

        Button retryButton = (Button) dialog.getDialogPane().lookupButton(retryButtonType);
        retryButton.setOnAction(event -> {
            logBnt.setDisable(true);
            Thread retryConnectionThread = new Thread(() -> {
                connectionManager.run();
                Platform.runLater(() -> {
                    if (connectionManager.getIsConnected()) {
                        logBnt.setDisable(false);
                        dialog.close();
                    } else {
                        showRetryDialog();
                    }
                });
            });
            retryConnectionThread.start();
        });

        dialog.showAndWait();
    }

    public void login(ActionEvent e) throws IOException {
        String username = nameTextField.getText();
        String password = passTextField.getText();

        connectionManager = ConnectionManager.getConnectionManagerInstance();
        JSONObject result = connectionManager.login(username, password);

        try {
        } catch (Exception ex) {

        }

        if (result.getString("command").equals("ok")) {
            try {
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
                GameLauncher.launchGame(result);
            } catch (Exception ex) {
                connectionManager.closeConnection();
                System.out.println(ex.toString());
            }
        } else {
            if (!connectionManager.getIsConnected()) {
                logBnt.setDisable(true);
                showRetryDialog();
            } else {
                connectionManager.run();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos");
                alert.showAndWait();
            }
        }
    }
}
