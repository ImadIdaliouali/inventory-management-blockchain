package com.enset.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.enset.models.User;
import com.enset.services.UserService;

public class AuthController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.authenticate(username, password);
        if (user != null) {
            try {
                // Load the main application window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));
                Parent root = loader.load();

                // Pass the logged-in user to the MainController
                MainController mainController = loader.getController();
                mainController.setLoggedInUser(user);

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Inventory Management");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to load FXML file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }
}