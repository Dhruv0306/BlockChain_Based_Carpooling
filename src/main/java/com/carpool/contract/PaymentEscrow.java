package com.carpool.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
 * <p>Generated with web3j version 1.6.3.
 */
@SuppressWarnings("rawtypes")
public class PaymentEscrow extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610c63806100206000396000f3fe60806040526004361061004a5760003560e01c80633f3f376a1461004f578063996d296314610078578063b6e906a214610094578063dbf26033146100d1578063dd7a0a7f1461010e575b600080fd5b34801561005b57600080fd5b50610076600480360381019061007191906106b3565b610137565b005b610092600480360381019061008d9190610672565b6102e2565b005b3480156100a057600080fd5b506100bb60048036038101906100b69190610672565b6103e6565b6040516100c89190610a61565b60405180910390f35b3480156100dd57600080fd5b506100f860048036038101906100f39190610672565b610414565b604051610105919061092a565b60405180910390f35b34801561011a57600080fd5b50610135600480360381019061013091906106b3565b61044a565b005b600080836040516101489190610913565b90815260200160405180910390205411610197576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161018e90610a01565b60405180910390fd5b6001826040516101a79190610913565b908152602001604051809103902060009054906101000a900460ff1615610203576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101fa906109c1565b60405180910390fd5b600080836040516102149190610913565b9081526020016040518091039020549050600180846040516102369190610913565b908152602001604051809103902060006101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156102a1573d6000803e3d6000fd5b507f82125553180669451df418ac47480ac5ee972192140731527b8579b69aad07878383836040516102d593929190610945565b60405180910390a1505050565b60003411610325576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161031c906109e1565b60405180910390fd5b600080826040516103369190610913565b90815260200160405180910390205414610385576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161037c90610a41565b60405180910390fd5b346000826040516103969190610913565b9081526020016040518091039020819055507f9a0b5a4e1e0c402d7e8c61035363c06016c72b3ce225a9a207826917346e485a8133346040516103db93929190610983565b60405180910390a150565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090505481565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6000808360405161045b9190610913565b908152602001604051809103902054116104aa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104a190610a21565b60405180910390fd5b6001826040516104ba9190610913565b908152602001604051809103902060009054906101000a900460ff1615610516576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161050d906109c1565b60405180910390fd5b600080836040516105279190610913565b9081526020016040518091039020549050600180846040516105499190610913565b908152602001604051809103902060006101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156105b4573d6000803e3d6000fd5b507f4f4233d8b608dab7ae8be019715573700f481096c3c0695cc3f8ff3bdc436bdd8383836040516105e893929190610945565b60405180910390a1505050565b600061060861060384610aad565b610a7c565b90508281526020810184848401111561062057600080fd5b61062b848285610b94565b509392505050565b60008135905061064281610c16565b92915050565b600082601f83011261065957600080fd5b81356106698482602086016105f5565b91505092915050565b60006020828403121561068457600080fd5b600082013567ffffffffffffffff81111561069e57600080fd5b6106aa84828501610648565b91505092915050565b600080604083850312156106c657600080fd5b600083013567ffffffffffffffff8111156106e057600080fd5b6106ec85828601610648565b92505060206106fd85828601610633565b9150509250929050565b61071081610b5e565b82525050565b61071f81610b04565b82525050565b61072e81610b28565b82525050565b600061073f82610add565b6107498185610ae8565b9350610759818560208601610ba3565b61076281610c05565b840191505092915050565b600061077882610add565b6107828185610af9565b9350610792818560208601610ba3565b80840191505092915050565b60006107ab601883610ae8565b91507f5061796d656e7420616c72656164792072656c656173656400000000000000006000830152602082019050919050565b60006107eb602583610ae8565b91507f5061796d656e7420616d6f756e74206d7573742062652067726561746572207460008301527f68616e20300000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000610851601583610ae8565b91507f4e6f207061796d656e7420746f2072656c6561736500000000000000000000006000830152602082019050919050565b6000610891601483610ae8565b91507f4e6f207061796d656e7420746f20726566756e640000000000000000000000006000830152602082019050919050565b60006108d1601983610ae8565b91507f5061796d656e7420616c7265616479206465706f7369746564000000000000006000830152602082019050919050565b61090d81610b54565b82525050565b600061091f828461076d565b915081905092915050565b600060208201905061093f6000830184610725565b92915050565b6000606082019050818103600083015261095f8186610734565b905061096e6020830185610707565b61097b6040830184610904565b949350505050565b6000606082019050818103600083015261099d8186610734565b90506109ac6020830185610716565b6109b96040830184610904565b949350505050565b600060208201905081810360008301526109da8161079e565b9050919050565b600060208201905081810360008301526109fa816107de565b9050919050565b60006020820190508181036000830152610a1a81610844565b9050919050565b60006020820190508181036000830152610a3a81610884565b9050919050565b60006020820190508181036000830152610a5a816108c4565b9050919050565b6000602082019050610a766000830184610904565b92915050565b6000604051905081810181811067ffffffffffffffff82111715610aa357610aa2610bd6565b5b8060405250919050565b600067ffffffffffffffff821115610ac857610ac7610bd6565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b600081905092915050565b6000610b0f82610b34565b9050919050565b6000610b2182610b34565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b6000610b6982610b70565b9050919050565b6000610b7b82610b82565b9050919050565b6000610b8d82610b34565b9050919050565b82818337600083830152505050565b60005b83811015610bc1578082015181840152602081019050610ba6565b83811115610bd0576000848401525b50505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b610c1f81610b16565b8114610c2a57600080fd5b5056fea2646970667358221220507677dff0d70e58f6488c4a1e4051385c298f80d1e0eb03dac2575305a8d14664736f6c63430008000033";

    private static String librariesLinkedBinary;

    public static final String FUNC_PAYMENTRELEASED = "paymentReleased";

    public static final String FUNC_RIDEPAYMENTS = "ridePayments";

    public static final String FUNC_DEPOSITPAYMENT = "depositPayment";

    public static final String FUNC_RELEASEPAYMENT = "releasePayment";

    public static final String FUNC_REFUNDPAYMENT = "refundPayment";

    public static final Event PAYMENTDEPOSITED_EVENT = new Event("PaymentDeposited", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAYMENTREFUNDED_EVENT = new Event("PaymentRefunded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAYMENTRELEASED_EVENT = new Event("PaymentReleased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("137", "0xEb08c9a0262B90f76A9B1B52691Ffd4ed9D12378");
    }

    @Deprecated
    protected PaymentEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PaymentEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PaymentEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PaymentEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<PaymentDepositedEventResponse> getPaymentDepositedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAYMENTDEPOSITED_EVENT, transactionReceipt);
        ArrayList<PaymentDepositedEventResponse> responses = new ArrayList<PaymentDepositedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentDepositedEventResponse typedResponse = new PaymentDepositedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PaymentDepositedEventResponse getPaymentDepositedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAYMENTDEPOSITED_EVENT, log);
        PaymentDepositedEventResponse typedResponse = new PaymentDepositedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<PaymentDepositedEventResponse> paymentDepositedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPaymentDepositedEventFromLog(log));
    }

    public Flowable<PaymentDepositedEventResponse> paymentDepositedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTDEPOSITED_EVENT));
        return paymentDepositedEventFlowable(filter);
    }

    public static List<PaymentRefundedEventResponse> getPaymentRefundedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAYMENTREFUNDED_EVENT, transactionReceipt);
        ArrayList<PaymentRefundedEventResponse> responses = new ArrayList<PaymentRefundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentRefundedEventResponse typedResponse = new PaymentRefundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PaymentRefundedEventResponse getPaymentRefundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAYMENTREFUNDED_EVENT, log);
        PaymentRefundedEventResponse typedResponse = new PaymentRefundedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<PaymentRefundedEventResponse> paymentRefundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPaymentRefundedEventFromLog(log));
    }

    public Flowable<PaymentRefundedEventResponse> paymentRefundedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTREFUNDED_EVENT));
        return paymentRefundedEventFlowable(filter);
    }

    public static List<PaymentReleasedEventResponse> getPaymentReleasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAYMENTRELEASED_EVENT, transactionReceipt);
        ArrayList<PaymentReleasedEventResponse> responses = new ArrayList<PaymentReleasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentReleasedEventResponse typedResponse = new PaymentReleasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PaymentReleasedEventResponse getPaymentReleasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAYMENTRELEASED_EVENT, log);
        PaymentReleasedEventResponse typedResponse = new PaymentReleasedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<PaymentReleasedEventResponse> paymentReleasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPaymentReleasedEventFromLog(log));
    }

    public Flowable<PaymentReleasedEventResponse> paymentReleasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTRELEASED_EVENT));
        return paymentReleasedEventFlowable(filter);
    }

    public RemoteFunctionCall<Boolean> paymentReleased(String param0) {
        final Function function = new Function(FUNC_PAYMENTRELEASED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> ridePayments(String param0) {
        final Function function = new Function(FUNC_RIDEPAYMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositPayment(String rideId,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DEPOSITPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> releasePayment(String rideId, String to) {
        final Function function = new Function(
                FUNC_RELEASEPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId), 
                new org.web3j.abi.datatypes.Address(to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> refundPayment(String rideId, String to) {
        final Function function = new Function(
                FUNC_REFUNDPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId), 
                new org.web3j.abi.datatypes.Address(to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PaymentEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new PaymentEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PaymentEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PaymentEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PaymentEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new PaymentEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PaymentEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PaymentEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PaymentEscrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PaymentEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<PaymentEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PaymentEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<PaymentEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PaymentEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<PaymentEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PaymentEscrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class PaymentDepositedEventResponse extends BaseEventResponse {
        public String rideId;

        public String from;

        public BigInteger amount;
    }

    public static class PaymentRefundedEventResponse extends BaseEventResponse {
        public String rideId;

        public String to;

        public BigInteger amount;
    }

    public static class PaymentReleasedEventResponse extends BaseEventResponse {
        public String rideId;

        public String to;

        public BigInteger amount;
    }
}
