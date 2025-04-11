/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamecloud;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author uriel
 */
public class Scene1Controller {
    @FXML
    TextField nameTextField;
    @FXML
    TextField passTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent e) throws IOException {
        String username = nameTextField.getText();
        String password = passTextField.getText();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

        GameLauncher.launchGame();
    }
}
