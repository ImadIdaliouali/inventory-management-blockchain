package com.enset.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.enset.models.User;
import com.enset.services.UserService;
import com.enset.App;

import java.io.IOException;

public class AuthController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Input Error", "Username and password cannot be empty.");
            return;
        }

        // Authenticate the user
        User user = userService.authenticate(username, password);
        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));
                Parent root = loader.load();

                MainController mainController = loader.getController();
                mainController.setLoggedInUser(user);

                App.changeScene("main");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load the application window.");
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
