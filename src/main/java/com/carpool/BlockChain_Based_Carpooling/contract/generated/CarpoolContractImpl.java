package com.carpool.BlockChain_Based_Carpooling.contract.generated;

import com.carpool.BlockChain_Based_Carpooling.contract.CarpoolContract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarpoolContractImpl extends Contract implements CarpoolContract {
    private static final String BINARY = "0x"; // Contract bytecode will go here

    public static final String FUNC_CREATERIDE = "createRide";
    public static final String FUNC_ACCEPTRIDE = "acceptRide";
    public static final String FUNC_COMPLETERIDE = "completeRide";
    public static final String FUNC_RECORDPAYMENT = "recordPayment";
    public static final String FUNC_GETRIDESTATUS = "getRideStatus";
    public static final String FUNC_GETRIDEDETAILS = "getRideDetails";

    public CarpoolContractImpl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, gasProvider);
    }

    @Override
    public RemoteFunctionCall<TransactionReceipt> createRide(
            String rideId,
            String sourceLocation,
            String destinationLocation,
            BigInteger departureTime,
            BigInteger availableSeats,
            BigInteger farePerSeat
    ) {
        final Function function = new Function(
                FUNC_CREATERIDE,
                Arrays.asList(
                        new Utf8String(rideId),
                        new Utf8String(sourceLocation),
                        new Utf8String(destinationLocation),
                        new Uint256(departureTime),
                        new Uint256(availableSeats),
                        new Uint256(farePerSeat)
                ),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    @Override
    public RemoteFunctionCall<TransactionReceipt> acceptRide(String rideId) {
        final Function function = new Function(
                FUNC_ACCEPTRIDE,
                Collections.singletonList(new Utf8String(rideId)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    @Override
    public RemoteFunctionCall<TransactionReceipt> completeRide(String rideId) {
        final Function function = new Function(
                FUNC_COMPLETERIDE,
                Collections.singletonList(new Utf8String(rideId)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    @Override
    public RemoteFunctionCall<TransactionReceipt> recordPayment(
            String rideId,
            String from,
            String to,
            BigInteger amount
    ) {
        final Function function = new Function(
                FUNC_RECORDPAYMENT,
                Arrays.asList(
                        new Utf8String(rideId),
                        new Utf8String(from),
                        new Utf8String(to),
                        new Uint256(amount)
                ),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    @Override
    public RemoteFunctionCall<String> getRideStatus(String rideId) {
        final Function function = new Function(
                FUNC_GETRIDESTATUS,
                Collections.singletonList(new Utf8String(rideId)),
                Collections.singletonList(new TypeReference<Utf8String>() {})
        );
        return new RemoteFunctionCall<String>(
                function,
                () -> executeCallSingleValueReturn(function, String.class)
        );
    }

    @Override
    public RemoteFunctionCall<Tuple2<String, String>> getRideDetails(String rideId) {
        final Function function = new Function(
                FUNC_GETRIDEDETAILS,
                Collections.singletonList(new Utf8String(rideId)),
                Arrays.asList(
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {}
                )
        );
        return new RemoteFunctionCall<>(
                function,
                () -> {
                    @SuppressWarnings("rawtypes")
                    List<Type> results = executeCallMultipleValueReturn(function);
                    return new Tuple2<>(
                        ((Utf8String) results.get(0)).getValue(),
                        ((Utf8String) results.get(1)).getValue()
                    );
                }
        );
    }

    public static CarpoolContractImpl load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider gasProvider
    ) {
        return new CarpoolContractImpl(contractAddress, web3j, transactionManager, gasProvider);
    }
} 