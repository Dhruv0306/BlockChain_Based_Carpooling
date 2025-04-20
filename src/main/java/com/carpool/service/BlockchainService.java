package com.carpool.service;

import com.carpool.contract.CarpoolContract;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;

/**
 * Service class to handle all blockchain-related operations
 */
@Service
public class BlockchainService {
    private final Web3j web3j;
    private final CarpoolContract contract;

    @Autowired
    public BlockchainService(Dotenv dotenv) {
        // Load environment variables
        String networkUrl = dotenv.get("ETHEREUM_NETWORK");
        String privateKey = dotenv.get("PRIVATE_KEY");
        String contractAddress = dotenv.get("CONTRACT_ADDRESS");
        
        // Initialize Web3j
        this.web3j = Web3j.build(new HttpService(networkUrl));
        
        // Create credentials
        Credentials credentials = Credentials.create(privateKey);
        
        // Initialize contract
        ContractGasProvider gasProvider = new DefaultGasProvider();
        this.contract = CarpoolContract.load(
            contractAddress,
            web3j,
            credentials,
            gasProvider
        );
    }

    /**
     * Create a new ride on the blockchain
     */
    public RemoteFunctionCall<TransactionReceipt> createRide(
            String rideId,
            String startLocation,
            String endLocation,
            BigInteger departureTime,
            BigInteger availableSeats,
            BigInteger fare) throws Exception {
        
        return contract.createRide(
            rideId,
            startLocation,
            endLocation,
            departureTime,
            availableSeats,
            fare
        );
    }

    /**
     * Request to join a ride
     */
    public RemoteFunctionCall<TransactionReceipt> requestRide(String rideId) throws Exception {
        return contract.requestRide(rideId);
    }

    /**
     * Accept a ride request
     */
    public RemoteFunctionCall<TransactionReceipt> acceptRideRequest(String rideId, String requester) throws Exception {
        return contract.acceptRideRequest(rideId, requester);
    }

    /**
     * Make payment for a ride
     */
    public RemoteFunctionCall<TransactionReceipt> makePayment(String rideId, BigInteger weiValue) throws Exception {
        return contract.makePayment(rideId, weiValue);
    }

    /**
     * Update user reputation
     */
    public RemoteFunctionCall<TransactionReceipt> updateReputation(String user, Boolean isPositive) throws Exception {
        return contract.updateReputation(user, isPositive);
    }

    /**
     * Get ride information from blockchain
     */
    public RemoteFunctionCall<CarpoolContract.RideInfo> getRideInfo(String rideId) throws Exception {
        return contract.getRideInfo(rideId);
    }

    /**
     * Check if a ride request is accepted
     */
    public RemoteFunctionCall<Boolean> isRideRequestAccepted(String rideId, String requester) throws Exception {
        return contract.isRideRequestAccepted(rideId, requester);
    }

    /**
     * Get user reputation score
     */
    public RemoteFunctionCall<BigInteger> getReputation(String user) throws Exception {
        return contract.getReputation(user);
    }
} 