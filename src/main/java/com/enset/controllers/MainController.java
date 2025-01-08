package com.enset.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.enset.models.Product;
import com.enset.models.Transaction;
import com.enset.models.User;
import com.enset.services.ProductService;
import com.enset.services.BlockchainService;
import com.enset.App;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Integer> transactionIdColumn;
    @FXML
    private TableColumn<Transaction, String> productNameColumn;
    @FXML
    private TableColumn<Transaction, Integer> transactionQuantityColumn;
    @FXML
    private TableColumn<Transaction, String> actionColumn;
    @FXML
    private TableColumn<Transaction, String> timestampColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;

    @FXML
    private Label userNameLabel;

    private ProductService productService = new ProductService();
    private BlockchainService blockchainService = new BlockchainService();
    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;

        if (userNameLabel != null) {
            userNameLabel.setText(user.getUsername());
            userNameLabel.requestLayout(); // Force UI refresh
            userNameLabel.applyCss(); // Apply CSS styles
        } else {

        }
    }

    @FXML
    public void initialize() {
        // Bind table columns to Product properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Bind table columns to Transaction properties
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        transactionQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestampAsString"));

        // Load data into the tables
        loadProducts();
        refreshTransactions();

        // Add listener to productTable selection
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                quantityField.setText(String.valueOf(newSelection.getQuantity()));
                priceField.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(productService.getAllProducts());
        productTable.setItems(products);
    }

    private void refreshTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        int transactionCount = blockchainService.getTransactionCount();

        for (int i = 1; i <= transactionCount; i++) {
            String transactionDetails = blockchainService.getTransaction(i);
            if (!transactionDetails.startsWith("Error")) {
                // Parse the transaction details
                String[] parts = transactionDetails.split(", ");
                int id = Integer.parseInt(parts[0].split(": ")[1]);
                String productName = parts[1].split(": ")[1];
                int quantity = Integer.parseInt(parts[2].split(": ")[1]);
                String action = parts[3].split(": ")[1];
                long timestamp = Long.parseLong(parts[4].split(": ")[1]);

                transactions.add(new Transaction(id, productName, quantity, action, timestamp));
            }
        }

        transactionTable.setItems(transactions);
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Product product = new Product(0, name, quantity, price);
        productService.addProduct(product);
        loadProducts();
        refreshTransactions();
        clearFields();
    }

    @FXML
    private void handleUpdateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update Product");
            alert.setHeaderText("Are you sure you want to update this product?");
            alert.setContentText("Product: " + selectedProduct.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                Product updatedProduct = new Product(selectedProduct.getId(), name, quantity, price);
                productService.updateProduct(updatedProduct);
                loadProducts();
                refreshTransactions();
                clearFields();
                showAlert("Update Successful", "The product has been updated successfully.",
                        Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Update Error", "Please select a product to update.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete this product?");
            alert.setContentText("Product: " + selectedProduct.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                productService.deleteProduct(selectedProduct.getId());
                loadProducts();
                refreshTransactions();
                clearFields();
                showAlert("Delete Successful", "The product has been deleted successfully.",
                        Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Delete Error", "Please select a product to delete.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be redirected to the login screen.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.changeScene("login");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Logout Error", "An error occurred while logging out.", Alert.AlertType.ERROR);
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
