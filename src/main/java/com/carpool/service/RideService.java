package com.carpool.service;

import com.carpool.config.ContractConfig;
import com.carpool.contract.CarpoolContract;
import com.carpool.model.Ride;
import com.carpool.model.Ride.RideStatus;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import org.web3j.crypto.Credentials;

@Service
public class RideService extends ContractService {
    
    private final CarpoolContract carpoolContract;
    private final ReputationTokenService reputationTokenService;
    private final PaymentEscrowService paymentEscrowService;

    public RideService(
        ContractConfig contractConfig,
        ReputationTokenService reputationTokenService,
        PaymentEscrowService paymentEscrowService
    ) {
        super(contractConfig);
        this.reputationTokenService = reputationTokenService;
        this.paymentEscrowService = paymentEscrowService;
        
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

    // Create a new ride
    public CompletableFuture<TransactionReceipt> createRide(
        String rideId,
        String startLocation,
        String endLocation,
        long departureTime,
        int availableSeats,
        long fare
    ) throws Exception {
        // Create ride on blockchain
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.createRide(
            rideId,
            startLocation,
            endLocation,
            BigInteger.valueOf(departureTime),
            BigInteger.valueOf(availableSeats),
            BigInteger.valueOf(fare)
        );
        
        // Reward driver with reputation points
        reputationTokenService.rateUser(getAccountAddress(), true);
        
        return tx.sendAsync();
    }

    // Request a ride
    public CompletableFuture<TransactionReceipt> requestRide(
        String rideId
    ) throws Exception {
        // Request ride on blockchain
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.requestRide(rideId);
        
        // Reward passenger with reputation points
        reputationTokenService.rateUser(getAccountAddress(), true);
        
        return tx.sendAsync();
    }

    // Accept a ride request
    public CompletableFuture<TransactionReceipt> acceptRideRequest(
        String rideId,
        String requester
    ) throws Exception {
        // Accept request on blockchain
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.acceptRideRequest(
            rideId,
            requester
        );
        
        // Reward both driver and passenger
        reputationTokenService.rateUser(getAccountAddress(), true);
        reputationTokenService.rateUser(requester, true);
        
        return tx.sendAsync();
    }

    // Make payment for a ride
    public CompletableFuture<TransactionReceipt> makePayment(
        String rideId,
        long amount
    ) throws Exception {
        // Make payment on blockchain
        RemoteFunctionCall<TransactionReceipt> tx = carpoolContract.makePayment(
            rideId,
            BigInteger.valueOf(amount)
        );
        
        // Deposit payment in escrow
        paymentEscrowService.depositPayment(rideId, BigInteger.valueOf(amount));
        
        // Reward passenger for timely payment
        reputationTokenService.rateUser(getAccountAddress(), true);
        
        return tx.sendAsync();
    }

    // Get ride details
    public Ride getRideDetails(String rideId) throws Exception {
        // Get ride info from blockchain
        CarpoolContract.RideInfo rideInfo = carpoolContract.getRideInfo(rideId).send();
        
        // Create a new Ride object
        Ride ride = new Ride();
        ride.setBlockchainRideId(rideInfo.rideId);
        ride.setStartLocation(rideInfo.startLocation);
        ride.setEndLocation(rideInfo.endLocation);
        ride.setDepartureTime(java.time.LocalDateTime.ofEpochSecond(
            rideInfo.departureTime.longValue(), 0, java.time.ZoneOffset.UTC));
        ride.setAvailableSeats(rideInfo.availableSeats.intValue());
        ride.setFare(rideInfo.fare.doubleValue());
        ride.setStatus(RideStatus.values()[rideInfo.status.intValue()]);
        
        return ride;
    }

    // Check if a ride request is accepted
    public boolean isRideRequestAccepted(String rideId, String requester) throws Exception {
        return carpoolContract.isRideRequestAccepted(rideId, requester).send();
    }
} 