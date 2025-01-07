package com.enset.services;

import com.enset.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private DatabaseService databaseService;
    private BlockchainService blockchainService;

    public ProductService() {
        databaseService = new DatabaseService();
        blockchainService = new BlockchainService();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (ResultSet resultSet = databaseService.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void addProduct(Product product) {
        String query = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";

        try (PreparedStatement statement = databaseService.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();

            // Add transaction to blockchain
            blockchainService.addTransaction(product.getName(), product.getQuantity(), "ADD");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";

        try (PreparedStatement statement = databaseService.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getId());
            statement.executeUpdate();

            // Record transaction on blockchain
            blockchainService.addTransaction(product.getName(), product.getQuantity(), "UPDATE");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement statement = databaseService.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();

            // Record transaction on blockchain
            blockchainService.addTransaction("Product ID: " + id, 0, "REMOVE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}