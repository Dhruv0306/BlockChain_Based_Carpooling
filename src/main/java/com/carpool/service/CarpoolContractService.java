package com.carpool.service;

import com.carpool.config.ContractConfig;
import com.carpool.contract.CarpoolContract;
import org.springframework.stereotype.Service;
import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import java.util.concurrent.CompletableFuture;
import org.web3j.crypto.Credentials;

@Service
public class CarpoolContractService extends ContractService {
    
    private final CarpoolContract carpoolContract;

    public CarpoolContractService(ContractConfig contractConfig) {
        super(contractConfig);
        ContractGasProvider gasProvider = getGasProvider();
        Credentials credentials = Credentials.create(getPrivateKey());
        this.carpoolContract = CarpoolContract.load(
            getCarpoolContractAddress(),
            getWeb3j(),
            credentials,
            gasProvider
        );
    }

    public CarpoolContract getContract() {
        return carpoolContract;
    }

    // Add methods for contract interactions
    public CompletableFuture<TransactionReceipt> createRide(
        String rideId,
        String startLocation,
        String endLocation,
        long departureTime,
        int availableSeats,
        long fare
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.createRide(
            rideId,
            startLocation,
            endLocation,
            BigInteger.valueOf(departureTime),
            BigInteger.valueOf(availableSeats),
            BigInteger.valueOf(fare)
        );
        return tx.sendAsync();
    }

    public CompletableFuture<TransactionReceipt> requestRide(
        String rideId
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.requestRide(rideId);
        return tx.sendAsync();
    }

    public CompletableFuture<TransactionReceipt> acceptRideRequest(
        String rideId,
        String requester
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.acceptRideRequest(
            rideId,
            requester
        );
        return tx.sendAsync();
    }

    public CompletableFuture<TransactionReceipt> makePayment(
        String rideId,
        long amount
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.makePayment(
            rideId,
            BigInteger.valueOf(amount)
        );
        return tx.sendAsync();
    }

    public CompletableFuture<TransactionReceipt> updateReputation(
        String userAddress,
        boolean isPositive
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.updateReputation(
            userAddress,
            isPositive
        );
        return tx.sendAsync();
    }

    public CompletableFuture<BigInteger> getReputation(
        String userAddress
    ) throws Exception {
        RemoteFunctionCall<BigInteger> call = carpoolContract.getReputation(userAddress);
        return call.sendAsync();
    }

    public CompletableFuture<Boolean> isRideRequestAccepted(
        String rideId,
        String requester
    ) throws Exception {
        RemoteFunctionCall<Boolean> call = carpoolContract.isRideRequestAccepted(rideId, requester);
        return call.sendAsync();
    }

    public CompletableFuture<CarpoolContract.RideInfo> getRideInfo(
        String rideId
    ) throws Exception {
        RemoteFunctionCall<CarpoolContract.RideInfo> call = carpoolContract.getRideInfo(rideId);
        return call.sendAsync();
    }
} 