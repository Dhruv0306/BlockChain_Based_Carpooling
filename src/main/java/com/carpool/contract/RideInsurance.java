package com.carpool.contract;

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
import org.web3j.tuples.generated.Tuple4;
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
public class RideInsurance extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610a43806100206000396000f3fe6080604052600436106100345760003560e01c806313500ca01461003957806357ffab8414610055578063e16d2bae14610095575b600080fd5b610053600480360381019061004e9190610502565b6100be565b005b34801561006157600080fd5b5061007c60048036038101906100779190610502565b61025c565b60405161008c94939291906107ed565b60405180910390f35b3480156100a157600080fd5b506100bc60048036038101906100b79190610502565b6102bc565b005b60003411610101576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016100f89061078d565b60405180910390fd5b60008160405161011191906106da565b908152602001604051809103902060020160009054906101000a900460ff1615610170576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101679061076d565b60405180910390fd5b600060033461017f91906108ba565b90506040518060800160405280348152602001828152602001600115158152602001600015158152506000836040516101b891906106da565b9081526020016040518091039020600082015181600001556020820151816001015560408201518160020160006101000a81548160ff02191690831515021790555060608201518160020160016101000a81548160ff0219169083151502179055509050507fd21329d518f9d70c381037c53f508d97c07ec5e4295d8d96cb3e69c93d8269e78234836040516102509392919061072f565b60405180910390a15050565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000154908060010154908060020160009054906101000a900460ff16908060020160019054906101000a900460ff16905084565b6000816040516102cc91906106da565b908152602001604051809103902060020160009054906101000a900460ff1661032a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610321906107ad565b60405180910390fd5b60008160405161033a91906106da565b908152602001604051809103902060020160019054906101000a900460ff1615610399576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610390906107cd565b60405180910390fd5b60016000826040516103ab91906106da565b908152602001604051809103902060020160016101000a81548160ff0219169083151502179055503373ffffffffffffffffffffffffffffffffffffffff166108fc6000836040516103fd91906106da565b9081526020016040518091039020600101549081150290604051600060405180830381858888f1935050505015801561043a573d6000803e3d6000fd5b507f787d893d48dd78acde64d225978e4e11cdd9cb85b845bf8c376f0b93bdb54601813360008460405161046e91906106da565b90815260200160405180910390206001015460405161048f939291906106f1565b60405180910390a150565b60006104ad6104a884610863565b610832565b9050828152602081018484840111156104c557600080fd5b6104d084828561095c565b509392505050565b600082601f8301126104e957600080fd5b81356104f984826020860161049a565b91505092915050565b60006020828403121561051457600080fd5b600082013567ffffffffffffffff81111561052e57600080fd5b61053a848285016104d8565b91505092915050565b61054c81610914565b82525050565b61055b81610926565b82525050565b600061056c82610893565b610576818561089e565b935061058681856020860161096b565b61058f816109fc565b840191505092915050565b60006105a582610893565b6105af81856108af565b93506105bf81856020860161096b565b80840191505092915050565b60006105d860188361089e565b91507f496e737572616e636520616c72656164792065786973747300000000000000006000830152602082019050919050565b6000610618601e8361089e565b91507f5072656d69756d206d7573742062652067726561746572207468616e203000006000830152602082019050919050565b600061065860138361089e565b91507f4e6f2061637469766520696e737572616e6365000000000000000000000000006000830152602082019050919050565b6000610698600f8361089e565b91507f416c726561647920636c61696d656400000000000000000000000000000000006000830152602082019050919050565b6106d481610952565b82525050565b60006106e6828461059a565b915081905092915050565b6000606082019050818103600083015261070b8186610561565b905061071a6020830185610543565b61072760408301846106cb565b949350505050565b600060608201905081810360008301526107498186610561565b905061075860208301856106cb565b61076560408301846106cb565b949350505050565b60006020820190508181036000830152610786816105cb565b9050919050565b600060208201905081810360008301526107a68161060b565b9050919050565b600060208201905081810360008301526107c68161064b565b9050919050565b600060208201905081810360008301526107e68161068b565b9050919050565b600060808201905061080260008301876106cb565b61080f60208301866106cb565b61081c6040830185610552565b6108296060830184610552565b95945050505050565b6000604051905081810181811067ffffffffffffffff82111715610859576108586109cd565b5b8060405250919050565b600067ffffffffffffffff82111561087e5761087d6109cd565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b600081905092915050565b60006108c582610952565b91506108d083610952565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff04831182151516156109095761090861099e565b5b828202905092915050565b600061091f82610932565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b8381101561098957808201518184015260208101905061096e565b83811115610998576000848401525b50505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f830116905091905056fea2646970667358221220eb3a8f5639d86caa55ba7c99c4b4e64692ec461f535480028b8b32d5de49f99864736f6c63430008000033";

    private static String librariesLinkedBinary;

    public static final String FUNC_RIDEINSURANCE = "rideInsurance";

    public static final String FUNC_PURCHASEINSURANCE = "purchaseInsurance";

    public static final String FUNC_FILECLAIM = "fileClaim";

    public static final Event CLAIMFILED_EVENT = new Event("ClaimFiled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event INSURANCEPURCHASED_EVENT = new Event("InsurancePurchased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("137", "0x54761E6C188689e374d8a3DE635011a3a054f6b5");
    }

    @Deprecated
    protected RideInsurance(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RideInsurance(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RideInsurance(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RideInsurance(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ClaimFiledEventResponse> getClaimFiledEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMFILED_EVENT, transactionReceipt);
        ArrayList<ClaimFiledEventResponse> responses = new ArrayList<ClaimFiledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimFiledEventResponse typedResponse = new ClaimFiledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.claimant = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClaimFiledEventResponse getClaimFiledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMFILED_EVENT, log);
        ClaimFiledEventResponse typedResponse = new ClaimFiledEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.claimant = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ClaimFiledEventResponse> claimFiledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimFiledEventFromLog(log));
    }

    public Flowable<ClaimFiledEventResponse> claimFiledEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMFILED_EVENT));
        return claimFiledEventFlowable(filter);
    }

    public static List<InsurancePurchasedEventResponse> getInsurancePurchasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INSURANCEPURCHASED_EVENT, transactionReceipt);
        ArrayList<InsurancePurchasedEventResponse> responses = new ArrayList<InsurancePurchasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InsurancePurchasedEventResponse typedResponse = new InsurancePurchasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.coverage = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InsurancePurchasedEventResponse getInsurancePurchasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INSURANCEPURCHASED_EVENT, log);
        InsurancePurchasedEventResponse typedResponse = new InsurancePurchasedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.coverage = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<InsurancePurchasedEventResponse> insurancePurchasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInsurancePurchasedEventFromLog(log));
    }

    public Flowable<InsurancePurchasedEventResponse> insurancePurchasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INSURANCEPURCHASED_EVENT));
        return insurancePurchasedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, Boolean, Boolean>> rideInsurance(
            String param0) {
        final Function function = new Function(FUNC_RIDEINSURANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, Boolean, Boolean> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, Boolean, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> purchaseInsurance(String rideId,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PURCHASEINSURANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> fileClaim(String rideId) {
        final Function function = new Function(
                FUNC_FILECLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static RideInsurance load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new RideInsurance(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RideInsurance load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RideInsurance(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RideInsurance load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new RideInsurance(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RideInsurance load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RideInsurance(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RideInsurance> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RideInsurance.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<RideInsurance> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RideInsurance.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<RideInsurance> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RideInsurance.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<RideInsurance> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RideInsurance.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class ClaimFiledEventResponse extends BaseEventResponse {
        public String rideId;

        public String claimant;

        public BigInteger amount;
    }

    public static class InsurancePurchasedEventResponse extends BaseEventResponse {
        public String rideId;

        public BigInteger premium;

        public BigInteger coverage;
    }
}
