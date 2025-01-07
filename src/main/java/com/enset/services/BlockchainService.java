package com.enset.services;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import com.enset.contracts.Inventory;

public class BlockchainService {
    private static final String CONTRACT_ADDRESS = "0x4070aa6C1667a923B1eEd3E42A746882086F4978";
    private static final String PRIVATE_KEY = "0x1de423cbf23b0a22394f0caf87c24fe62737a53fef041ad38a09654ef63eaa15";
    private static final String RPC_URL = "http://127.0.0.1:8545";

    private Web3j web3j;
    private Inventory contract;

    public BlockchainService() {
        web3j = Web3j.build(new HttpService(RPC_URL));
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        contract = Inventory.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
    }

    public void addTransaction(String productName, int quantity, String action) {
        try {
            // Convert int to BigInteger
            BigInteger quantityBigInt = BigInteger.valueOf(quantity);
            contract.addTransaction(productName, quantityBigInt, action).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTransaction(int id) {
        try {
            // Call the getTransaction function and retrieve individual fields
            BigInteger transactionId = BigInteger.valueOf(id);
            var result = contract.getTransaction(transactionId).send();

            // Extract fields from the result
            BigInteger idResult = result.component1();
            String productName = result.component2();
            BigInteger quantity = result.component3();
            String action = result.component4();
            BigInteger timestamp = result.component5();

            return String.format(
                    "ID: %d, Product: %s, Quantity: %d, Action: %s, Timestamp: %d",
                    idResult,
                    productName,
                    quantity,
                    action,
                    timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching transaction";
        }
    }
}