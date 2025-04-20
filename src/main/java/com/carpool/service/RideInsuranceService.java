package com.carpool.service;

import com.carpool.config.ContractConfig;
import com.carpool.contract.RideInsurance;
import org.springframework.stereotype.Service;
import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import java.util.concurrent.CompletableFuture;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.crypto.Credentials;

/**
 * Service class for handling ride insurance operations on the blockchain
 */
@Service
public class RideInsuranceService extends ContractService {
    
    private final RideInsurance rideInsurance;

    public RideInsuranceService(ContractConfig contractConfig) {
        super(contractConfig);
        ContractGasProvider gasProvider = getGasProvider();
        Credentials credentials = Credentials.create(getPrivateKey());
        this.rideInsurance = RideInsurance.load(
            getInsuranceContractAddress(),
            getWeb3j(),
            credentials,
            gasProvider
        );
    }

    public RideInsurance getContract() {
        return rideInsurance;
    }

    /**
     * Purchase insurance for a ride
     * @param rideId The ID of the ride
     * @param premium The insurance premium in wei
     */
    public CompletableFuture<TransactionReceipt> purchaseInsurance(
        String rideId,
        BigInteger premium
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = rideInsurance.purchaseInsurance(rideId, premium);
        return tx.sendAsync();
    }

    /**
     * File an insurance claim for a ride
     * @param rideId The ID of the ride
     */
    public CompletableFuture<TransactionReceipt> fileClaim(
        String rideId
    ) throws Exception {
        RemoteFunctionCall<TransactionReceipt> tx = rideInsurance.fileClaim(rideId);
        return tx.sendAsync();
    }

    /**
     * Get insurance information for a ride
     * @param rideId The ID of the ride
     * @return A tuple containing (premium, coverage, active, claimed)
     */
    public CompletableFuture<Tuple4<BigInteger, BigInteger, Boolean, Boolean>> getRideInsurance(
        String rideId
    ) throws Exception {
        RemoteFunctionCall<Tuple4<BigInteger, BigInteger, Boolean, Boolean>> call = rideInsurance.rideInsurance(rideId);
        return call.sendAsync();
    }
} 