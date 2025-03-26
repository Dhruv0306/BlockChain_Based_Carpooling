package com.carpool.BlockChain_Based_Carpooling.service;

import com.carpool.BlockChain_Based_Carpooling.contract.generated.CarpoolContractImpl;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Service
public class BlockchainService {
    private final Web3j web3j;
    private final ContractGasProvider gasProvider;
    private CarpoolContractImpl carpoolContract;

    public BlockchainService() throws Exception {
        this.web3j = Web3j.build(new HttpService("https://rpc-mumbai.maticvigil.com"));
        this.gasProvider = new StaticGasProvider(
            BigInteger.valueOf(20_000_000_000L), // 20 Gwei
            BigInteger.valueOf(3_000_000L)
        );
        
        Credentials credentials = Credentials.create(System.getenv("BLOCKCHAIN_WALLET_PRIVATE_KEY"));
        this.carpoolContract = CarpoolContractImpl.load(
                System.getenv("BLOCKCHAIN_CONTRACT_ADDRESS"), web3j,
                new RawTransactionManager(web3j, credentials),
                gasProvider);
    }

    public String createRideOnBlockchain(
            String sourceLocation,
            String destinationLocation,
            LocalDateTime departureTime,
            Integer availableSeats,
            Double farePerSeat
    ) throws Exception {
        String rideId = java.util.UUID.randomUUID().toString();
        TransactionReceipt receipt = carpoolContract.createRide(
            rideId,
            sourceLocation,
            destinationLocation,
            BigInteger.valueOf(departureTime.toEpochSecond(ZoneOffset.UTC)),
            BigInteger.valueOf(availableSeats),
            BigInteger.valueOf((long) (farePerSeat * 100)) // Convert to smallest unit
        ).send();

        return receipt.getTransactionHash();
    }

    public void acceptRideOnBlockchain(String rideId) throws Exception {
        carpoolContract.acceptRide(rideId).send();
    }

    public void completeRideOnBlockchain(String rideId) throws Exception {
        carpoolContract.completeRide(rideId).send();
    }

    public void recordPaymentOnBlockchain(
            String rideId,
            String fromAddress,
            String toAddress,
            Double amount
    ) throws Exception {
        carpoolContract.recordPayment(
            rideId,
            fromAddress,
            toAddress,
            BigInteger.valueOf((long) (amount * 100)) // Convert to smallest unit
        ).send();
    }

    public String getRideStatusFromBlockchain(String rideId) throws Exception {
        return carpoolContract.getRideStatus(rideId).send();
    }

    public Tuple2<String, String> getRideDetailsFromBlockchain(String rideId) throws Exception {
        return carpoolContract.getRideDetails(rideId).send();
    }
}
