package com.carpool.Carpooling.service;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Service
public class BlockchainService {
    private final Web3j web3j;
    private final RideBookings rideBookings;

    public BlockchainService() throws Exception {
        this.web3j = Web3j.build(new HttpService("https://rpc-mumbai.maticvigil.com"));
        Credentials credentials = Credentials.create(System.getenv("BLOCKCHAIN_WALLET_PRIVATE_KEY"));
        this.rideBookings = RideBooking.load(
                System.getenv("BLOCKCHAIN_CONTRACT_ADDRESS"), web3j,
                new RawTransactionManager(web3j, credentials),
                new DefaultGasProvider());
    }

    public String postRide(String pickup, String dropoff, BigInteger fare, BigInteger seats) throws Exception {
        TransactionReceipt receipt = rideBookings.postRide(pickup, dropoff, fare, seats).send();
        return receipt.getTransactionHash();
    }
}
