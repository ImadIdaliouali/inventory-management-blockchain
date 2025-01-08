package com.enset.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final int id;
    private final String productName;
    private final int quantity;
    private final String action;
    private final long timestamp;

    public Transaction(int id, String productName, int quantity, String action, long timestamp) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.action = action;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAction() {
        return action;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Convert timestamp to LocalDateTime
    public LocalDateTime getTimestampAsLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    // Convert timestamp to formatted String
    public String getTimestampAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getTimestampAsLocalDateTime().format(formatter);
    }
}