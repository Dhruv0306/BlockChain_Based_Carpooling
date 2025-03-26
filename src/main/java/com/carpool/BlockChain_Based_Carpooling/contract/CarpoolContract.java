package com.carpool.BlockChain_Based_Carpooling.contract;

import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;

import java.math.BigInteger;

public interface CarpoolContract {
    // Create a new ride
    RemoteFunctionCall<TransactionReceipt> createRide(
        String rideId,
        String sourceLocation,
        String destinationLocation,
        BigInteger departureTime,
        BigInteger availableSeats,
        BigInteger farePerSeat
    );

    // Accept a ride
    RemoteFunctionCall<TransactionReceipt> acceptRide(String rideId);

    // Complete a ride
    RemoteFunctionCall<TransactionReceipt> completeRide(String rideId);

    // Get ride details
    RemoteFunctionCall<Tuple2<String, String>> getRideDetails(String rideId);

    // Get ride status
    RemoteFunctionCall<String> getRideStatus(String rideId);

    // Record payment
    RemoteFunctionCall<TransactionReceipt> recordPayment(
        String rideId,
        String from,
        String to,
        BigInteger amount
    );
} 