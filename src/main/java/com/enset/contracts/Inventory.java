package com.enset.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.2.
 */
@SuppressWarnings("rawtypes")
public class Inventory extends Contract {
    public static final String BINARY = "0x608060405234801561000f575f80fd5b5061100d8061001d5f395ff3fe608060405234801561000f575f80fd5b5060043610610054575f3560e01c80620ead301461005857806327506f531461007457806333ea3dc8146100925780639ace38c2146100c6578063b77bf600146100fa575b5f80fd5b610072600480360381019061006d9190610872565b610118565b005b61007c61020f565b6040516100899190610ab8565b60405180910390f35b6100ac60048036038101906100a79190610ad8565b6103a7565b6040516100bd959493929190610b5a565b60405180910390f35b6100e060048036038101906100db9190610ad8565b6105a0565b6040516100f1959493929190610b5a565b60405180910390f35b6101026106ec565b60405161010f9190610bb9565b60405180910390f35b60015f81548092919061012a90610bff565b91905055505f6040518060a00160405280600154815260200185815260200184815260200183815260200142815250908060018154018082558091505060019003905f5260205f2090600502015f909190919091505f820151815f0155602082015181600101908161019c9190610e40565b506040820151816002015560608201518160030190816101bc9190610e40565b506080820151816004015550507f3196db4d041d05ccfd3d6034aed07e8bb16710b1ca01ea82e90ef8df3fa8f68f60015484848442604051610202959493929190610b5a565b60405180910390a1505050565b60605f805480602002602001604051908101604052809291908181526020015f905b8282101561039e578382905f5260205f2090600502016040518060a00160405290815f820154815260200160018201805461026b90610c73565b80601f016020809104026020016040519081016040528092919081815260200182805461029790610c73565b80156102e25780601f106102b9576101008083540402835291602001916102e2565b820191905f5260205f20905b8154815290600101906020018083116102c557829003601f168201915b505050505081526020016002820154815260200160038201805461030590610c73565b80601f016020809104026020016040519081016040528092919081815260200182805461033190610c73565b801561037c5780601f106103535761010080835404028352916020019161037c565b820191905f5260205f20905b81548152906001019060200180831161035f57829003601f168201915b5050505050815260200160048201548152505081526020019060010190610231565b50505050905090565b5f60605f60605f80861180156103bf57506001548611155b6103fe576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103f590610f59565b60405180910390fd5b5f8060018861040d9190610f77565b8154811061041e5761041d610faa565b5b905f5260205f2090600502016040518060a00160405290815f820154815260200160018201805461044e90610c73565b80601f016020809104026020016040519081016040528092919081815260200182805461047a90610c73565b80156104c55780601f1061049c576101008083540402835291602001916104c5565b820191905f5260205f20905b8154815290600101906020018083116104a857829003601f168201915b50505050508152602001600282015481526020016003820180546104e890610c73565b80601f016020809104026020016040519081016040528092919081815260200182805461051490610c73565b801561055f5780601f106105365761010080835404028352916020019161055f565b820191905f5260205f20905b81548152906001019060200180831161054257829003601f168201915b505050505081526020016004820154815250509050805f01518160200151826040015183606001518460800151955095509550955095505091939590929450565b5f81815481106105ae575f80fd5b905f5260205f2090600502015f91509050805f0154908060010180546105d390610c73565b80601f01602080910402602001604051908101604052809291908181526020018280546105ff90610c73565b801561064a5780601f106106215761010080835404028352916020019161064a565b820191905f5260205f20905b81548152906001019060200180831161062d57829003601f168201915b50505050509080600201549080600301805461066590610c73565b80601f016020809104026020016040519081016040528092919081815260200182805461069190610c73565b80156106dc5780601f106106b3576101008083540402835291602001916106dc565b820191905f5260205f20905b8154815290600101906020018083116106bf57829003601f168201915b5050505050908060040154905085565b60015481565b5f604051905090565b5f80fd5b5f80fd5b5f80fd5b5f80fd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6107518261070b565b810181811067ffffffffffffffff821117156107705761076f61071b565b5b80604052505050565b5f6107826106f2565b905061078e8282610748565b919050565b5f67ffffffffffffffff8211156107ad576107ac61071b565b5b6107b68261070b565b9050602081019050919050565b828183375f83830152505050565b5f6107e36107de84610793565b610779565b9050828152602081018484840111156107ff576107fe610707565b5b61080a8482856107c3565b509392505050565b5f82601f83011261082657610825610703565b5b81356108368482602086016107d1565b91505092915050565b5f819050919050565b6108518161083f565b811461085b575f80fd5b50565b5f8135905061086c81610848565b92915050565b5f805f60608486031215610889576108886106fb565b5b5f84013567ffffffffffffffff8111156108a6576108a56106ff565b5b6108b286828701610812565b93505060206108c38682870161085e565b925050604084013567ffffffffffffffff8111156108e4576108e36106ff565b5b6108f086828701610812565b9150509250925092565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b61092c8161083f565b82525050565b5f81519050919050565b5f82825260208201905092915050565b5f5b8381101561096957808201518184015260208101905061094e565b5f8484015250505050565b5f61097e82610932565b610988818561093c565b935061099881856020860161094c565b6109a18161070b565b840191505092915050565b5f60a083015f8301516109c15f860182610923565b50602083015184820360208601526109d98282610974565b91505060408301516109ee6040860182610923565b5060608301518482036060860152610a068282610974565b9150506080830151610a1b6080860182610923565b508091505092915050565b5f610a3183836109ac565b905092915050565b5f602082019050919050565b5f610a4f826108fa565b610a598185610904565b935083602082028501610a6b85610914565b805f5b85811015610aa65784840389528151610a878582610a26565b9450610a9283610a39565b925060208a01995050600181019050610a6e565b50829750879550505050505092915050565b5f6020820190508181035f830152610ad08184610a45565b905092915050565b5f60208284031215610aed57610aec6106fb565b5b5f610afa8482850161085e565b91505092915050565b610b0c8161083f565b82525050565b5f82825260208201905092915050565b5f610b2c82610932565b610b368185610b12565b9350610b4681856020860161094c565b610b4f8161070b565b840191505092915050565b5f60a082019050610b6d5f830188610b03565b8181036020830152610b7f8187610b22565b9050610b8e6040830186610b03565b8181036060830152610ba08185610b22565b9050610baf6080830184610b03565b9695505050505050565b5f602082019050610bcc5f830184610b03565b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610c098261083f565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610c3b57610c3a610bd2565b5b600182019050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680610c8a57607f821691505b602082108103610c9d57610c9c610c46565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302610cff7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610cc4565b610d098683610cc4565b95508019841693508086168417925050509392505050565b5f819050919050565b5f610d44610d3f610d3a8461083f565b610d21565b61083f565b9050919050565b5f819050919050565b610d5d83610d2a565b610d71610d6982610d4b565b848454610cd0565b825550505050565b5f90565b610d85610d79565b610d90818484610d54565b505050565b5b81811015610db357610da85f82610d7d565b600181019050610d96565b5050565b601f821115610df857610dc981610ca3565b610dd284610cb5565b81016020851015610de1578190505b610df5610ded85610cb5565b830182610d95565b50505b505050565b5f82821c905092915050565b5f610e185f1984600802610dfd565b1980831691505092915050565b5f610e308383610e09565b9150826002028217905092915050565b610e4982610932565b67ffffffffffffffff811115610e6257610e6161071b565b5b610e6c8254610c73565b610e77828285610db7565b5f60209050601f831160018114610ea8575f8415610e96578287015190505b610ea08582610e25565b865550610f07565b601f198416610eb686610ca3565b5f5b82811015610edd57848901518255600182019150602085019450602081019050610eb8565b86831015610efa5784890151610ef6601f891682610e09565b8355505b6001600288020188555050505b505050505050565b7f496e76616c6964207472616e73616374696f6e204944000000000000000000005f82015250565b5f610f43601683610b12565b9150610f4e82610f0f565b602082019050919050565b5f6020820190508181035f830152610f7081610f37565b9050919050565b5f610f818261083f565b9150610f8c8361083f565b9250828203905081811115610fa457610fa3610bd2565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffdfea26469706673582212200faf907ef4e8ce50321724175dc97dc851610efaf6a75a0a8a4407cacbf95b3764736f6c63430008150033";

    private static String librariesLinkedBinary;

    public static final String FUNC_TRANSACTIONCOUNT = "transactionCount";

    public static final String FUNC_TRANSACTIONS = "transactions";

    public static final String FUNC_ADDTRANSACTION = "addTransaction";

    public static final String FUNC_GETTRANSACTION = "getTransaction";

    public static final String FUNC_GETALLTRANSACTIONS = "getAllTransactions";

    public static final Event TRANSACTIONADDED_EVENT = new Event("TransactionAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1736348784756", "0x5fE63F669bD7F6442DA83312b4e18A30C185f70c");
        _addresses.put("1736335134248", "0xa44C2f3D91Fb45176d9aE312f3F225C99bFAa0E8");
    }

    @Deprecated
    protected Inventory(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Inventory(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Inventory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Inventory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<TransactionAddedEventResponse> getTransactionAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSACTIONADDED_EVENT, transactionReceipt);
        ArrayList<TransactionAddedEventResponse> responses = new ArrayList<TransactionAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransactionAddedEventResponse typedResponse = new TransactionAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.productName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.action = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransactionAddedEventResponse getTransactionAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSACTIONADDED_EVENT, log);
        TransactionAddedEventResponse typedResponse = new TransactionAddedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.productName = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.action = (String) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<TransactionAddedEventResponse> transactionAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransactionAddedEventFromLog(log));
    }

    public Flowable<TransactionAddedEventResponse> transactionAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSACTIONADDED_EVENT));
        return transactionAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> transactionCount() {
        final Function function = new Function(FUNC_TRANSACTIONCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, String, BigInteger>> transactions(
            BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, BigInteger, String, BigInteger> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, BigInteger, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> addTransaction(String _productName,
            BigInteger _quantity, String _action) {
        final Function function = new Function(
                FUNC_ADDTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_productName), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity), 
                new org.web3j.abi.datatypes.Utf8String(_action)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, String, BigInteger>> getTransaction(
            BigInteger _id) {
        final Function function = new Function(FUNC_GETTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, BigInteger, String, BigInteger> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, BigInteger, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getAllTransactions() {
        final Function function = new Function(FUNC_GETALLTRANSACTIONS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Transaction>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static Inventory load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Inventory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Inventory load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Inventory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Inventory load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Inventory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Inventory load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Inventory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Inventory> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Inventory.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Inventory> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Inventory.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<Inventory> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Inventory.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Inventory> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Inventory.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class Transaction extends DynamicStruct {
        public BigInteger id;

        public String productName;

        public BigInteger quantity;

        public String action;

        public BigInteger timestamp;

        public Transaction(BigInteger id, String productName, BigInteger quantity, String action,
                BigInteger timestamp) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(productName), 
                    new org.web3j.abi.datatypes.generated.Uint256(quantity), 
                    new org.web3j.abi.datatypes.Utf8String(action), 
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp));
            this.id = id;
            this.productName = productName;
            this.quantity = quantity;
            this.action = action;
            this.timestamp = timestamp;
        }

        public Transaction(Uint256 id, Utf8String productName, Uint256 quantity, Utf8String action,
                Uint256 timestamp) {
            super(id, productName, quantity, action, timestamp);
            this.id = id.getValue();
            this.productName = productName.getValue();
            this.quantity = quantity.getValue();
            this.action = action.getValue();
            this.timestamp = timestamp.getValue();
        }
    }

    public static class TransactionAddedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String productName;

        public BigInteger quantity;

        public String action;

        public BigInteger timestamp;
    }
}
