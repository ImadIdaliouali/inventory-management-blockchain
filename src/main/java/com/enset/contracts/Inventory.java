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
    public static final String BINARY = "0x608060405234801561000f575f80fd5b50610be88061001d5f395ff3fe608060405234801561000f575f80fd5b5060043610610049575f3560e01c80620ead301461004d57806333ea3dc8146100695780639ace38c21461009d578063b77bf600146100d1575b5f80fd5b61006760048036038101906100629190610661565b6100ef565b005b610083600480360381019061007e91906106e9565b6101e6565b60405161009495949392919061079d565b60405180910390f35b6100b760048036038101906100b291906106e9565b61038f565b6040516100c895949392919061079d565b60405180910390f35b6100d96104db565b6040516100e691906107fc565b60405180910390f35b60015f81548092919061010190610842565b91905055505f6040518060a00160405280600154815260200185815260200184815260200183815260200142815250908060018154018082558091505060019003905f5260205f2090600502015f909190919091505f820151815f015560208201518160010190816101739190610a83565b506040820151816002015560608201518160030190816101939190610a83565b506080820151816004015550507f3196db4d041d05ccfd3d6034aed07e8bb16710b1ca01ea82e90ef8df3fa8f68f600154848484426040516101d995949392919061079d565b60405180910390a1505050565b5f60605f60605f805f6001886101fc9190610b52565b8154811061020d5761020c610b85565b5b905f5260205f2090600502016040518060a00160405290815f820154815260200160018201805461023d906108b6565b80601f0160208091040260200160405190810160405280929190818152602001828054610269906108b6565b80156102b45780601f1061028b576101008083540402835291602001916102b4565b820191905f5260205f20905b81548152906001019060200180831161029757829003601f168201915b50505050508152602001600282015481526020016003820180546102d7906108b6565b80601f0160208091040260200160405190810160405280929190818152602001828054610303906108b6565b801561034e5780601f106103255761010080835404028352916020019161034e565b820191905f5260205f20905b81548152906001019060200180831161033157829003601f168201915b505050505081526020016004820154815250509050805f01518160200151826040015183606001518460800151955095509550955095505091939590929450565b5f818154811061039d575f80fd5b905f5260205f2090600502015f91509050805f0154908060010180546103c2906108b6565b80601f01602080910402602001604051908101604052809291908181526020018280546103ee906108b6565b80156104395780601f1061041057610100808354040283529160200191610439565b820191905f5260205f20905b81548152906001019060200180831161041c57829003601f168201915b505050505090806002015490806003018054610454906108b6565b80601f0160208091040260200160405190810160405280929190818152602001828054610480906108b6565b80156104cb5780601f106104a2576101008083540402835291602001916104cb565b820191905f5260205f20905b8154815290600101906020018083116104ae57829003601f168201915b5050505050908060040154905085565b60015481565b5f604051905090565b5f80fd5b5f80fd5b5f80fd5b5f80fd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b610540826104fa565b810181811067ffffffffffffffff8211171561055f5761055e61050a565b5b80604052505050565b5f6105716104e1565b905061057d8282610537565b919050565b5f67ffffffffffffffff82111561059c5761059b61050a565b5b6105a5826104fa565b9050602081019050919050565b828183375f83830152505050565b5f6105d26105cd84610582565b610568565b9050828152602081018484840111156105ee576105ed6104f6565b5b6105f98482856105b2565b509392505050565b5f82601f830112610615576106146104f2565b5b81356106258482602086016105c0565b91505092915050565b5f819050919050565b6106408161062e565b811461064a575f80fd5b50565b5f8135905061065b81610637565b92915050565b5f805f60608486031215610678576106776104ea565b5b5f84013567ffffffffffffffff811115610695576106946104ee565b5b6106a186828701610601565b93505060206106b28682870161064d565b925050604084013567ffffffffffffffff8111156106d3576106d26104ee565b5b6106df86828701610601565b9150509250925092565b5f602082840312156106fe576106fd6104ea565b5b5f61070b8482850161064d565b91505092915050565b61071d8161062e565b82525050565b5f81519050919050565b5f82825260208201905092915050565b5f5b8381101561075a57808201518184015260208101905061073f565b5f8484015250505050565b5f61076f82610723565b610779818561072d565b935061078981856020860161073d565b610792816104fa565b840191505092915050565b5f60a0820190506107b05f830188610714565b81810360208301526107c28187610765565b90506107d16040830186610714565b81810360608301526107e38185610765565b90506107f26080830184610714565b9695505050505050565b5f60208201905061080f5f830184610714565b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61084c8261062e565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361087e5761087d610815565b5b600182019050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f60028204905060018216806108cd57607f821691505b6020821081036108e0576108df610889565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026109427fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610907565b61094c8683610907565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61098761098261097d8461062e565b610964565b61062e565b9050919050565b5f819050919050565b6109a08361096d565b6109b46109ac8261098e565b848454610913565b825550505050565b5f90565b6109c86109bc565b6109d3818484610997565b505050565b5b818110156109f6576109eb5f826109c0565b6001810190506109d9565b5050565b601f821115610a3b57610a0c816108e6565b610a15846108f8565b81016020851015610a24578190505b610a38610a30856108f8565b8301826109d8565b50505b505050565b5f82821c905092915050565b5f610a5b5f1984600802610a40565b1980831691505092915050565b5f610a738383610a4c565b9150826002028217905092915050565b610a8c82610723565b67ffffffffffffffff811115610aa557610aa461050a565b5b610aaf82546108b6565b610aba8282856109fa565b5f60209050601f831160018114610aeb575f8415610ad9578287015190505b610ae38582610a68565b865550610b4a565b601f198416610af9866108e6565b5f5b82811015610b2057848901518255600182019150602085019450602081019050610afb565b86831015610b3d5784890151610b39601f891682610a4c565b8355505b6001600288020188555050505b505050505050565b5f610b5c8261062e565b9150610b678361062e565b9250828203905081811115610b7f57610b7e610815565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffdfea264697066735822122013f01b34a13933dd0b1ed5dc93c41fba1ee27d74236defc972ec3970c2cb70d064736f6c63430008150033";

    private static String librariesLinkedBinary;

    public static final String FUNC_TRANSACTIONCOUNT = "transactionCount";

    public static final String FUNC_TRANSACTIONS = "transactions";

    public static final String FUNC_ADDTRANSACTION = "addTransaction";

    public static final String FUNC_GETTRANSACTION = "getTransaction";

    public static final Event TRANSACTIONADDED_EVENT = new Event("TransactionAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1736279659062", "0x4070aa6C1667a923B1eEd3E42A746882086F4978");
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

    public static class TransactionAddedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String productName;

        public BigInteger quantity;

        public String action;

        public BigInteger timestamp;
    }
}
