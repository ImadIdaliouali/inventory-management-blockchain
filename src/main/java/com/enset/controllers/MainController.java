package com.enset.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.enset.models.Product;
import com.enset.models.User;
import com.enset.services.ProductService;

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
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;

    private ProductService productService = new ProductService();
    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        System.out.println("Logged in as: " + user.getUsername() + " (" + user.getRole() + ")");
    }

    @FXML
    public void initialize() {
        // Bind table columns to Product properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load data into the table
        loadProducts();
    }

    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(productService.getAllProducts());
        productTable.setItems(products);
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Product product = new Product(0, name, quantity, price);
        productService.addProduct(product);
        loadProducts();
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productService.deleteProduct(selectedProduct.getId());
            loadProducts();
        }
    }
}