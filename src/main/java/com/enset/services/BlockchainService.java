package com.enset.services;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import com.enset.contracts.Inventory;

public class BlockchainService {
    private static final String CONTRACT_ADDRESS = "0x746bbe0397f2c83b98c9ea6d526f30735fe685a6";
    private static final String PRIVATE_KEY = "0x76f05b239620e8b51d4c66ede5f3ca027e96bf21aab36737a5662bc60ca09e63";
    private static final String RPC_URL = "http://127.0.0.1:8545";

    private Web3j web3j;
    private Inventory contract;

    public BlockchainService() {
        try {
            // Initialize Web3j and contract
            web3j = Web3j.build(new HttpService(RPC_URL));
            Credentials credentials = Credentials.create(PRIVATE_KEY);
            contract = Inventory.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
        } catch (Exception e) {
            System.err.println("Error initializing BlockchainService: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize BlockchainService", e);
        }
    }

    public void addTransaction(String productName, int quantity, String action) {
        try {
            BigInteger quantityBigInt = BigInteger.valueOf(quantity);
            contract.addTransaction(productName, quantityBigInt, action).send();

        } catch (Exception e) {
            System.err.println("Error adding transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getTransactionCount() {
        try {
            BigInteger transactionCount = contract.transactionCount().send();

            return transactionCount.intValue();
        } catch (Exception e) {
            System.err.println("Error fetching transaction count: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public String getTransaction(int id) {
        try {
            BigInteger transactionId = BigInteger.valueOf(id);
            var result = contract.getTransaction(transactionId).send();

            // Extract fields from the result
            BigInteger idResult = result.component1();
            String productName = result.component2();
            BigInteger quantity = result.component3();
            String action = result.component4();
            BigInteger timestamp = result.component5();

            String transactionDetails = String.format(
                    "ID: %d, Product: %s, Quantity: %d, Action: %s, Timestamp: %d",
                    idResult, productName, quantity, action, timestamp);

            return transactionDetails;
        } catch (Exception e) {
            System.err.println("Error fetching transaction: " + e.getMessage());
            e.printStackTrace();
            return "Error fetching transaction";
        }
    }
}
