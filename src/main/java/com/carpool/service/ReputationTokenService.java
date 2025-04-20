package com.carpool.service;

import com.carpool.config.ContractConfig;
import com.carpool.contract.ReputationToken;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;
import org.web3j.crypto.Credentials;

@Service
public class ReputationTokenService extends ContractService {
    
    private final ReputationToken reputationToken;

    public ReputationTokenService(ContractConfig contractConfig) {
        super(contractConfig);
        ContractGasProvider gasProvider = getGasProvider();
        Credentials credentials = Credentials.create(getPrivateKey());
        this.reputationToken = ReputationToken.load(
            getReputationContractAddress(),
            getWeb3j(),
            credentials,
            gasProvider
        );
    }

    public ReputationToken getContract() {
        return reputationToken;
    }

    // Add methods for contract interactions
    public RemoteFunctionCall<TransactionReceipt> rateUser(
        String userAddress,
        boolean isPositive
    ) throws Exception {
        return reputationToken.rateUser(userAddress, isPositive);
    }

    public RemoteFunctionCall<BigInteger> getReputationScore(
        String userAddress
    ) throws Exception {
        return reputationToken.getReputationScore(userAddress);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(
        String userAddress
    ) throws Exception {
        return reputationToken.balanceOf(userAddress);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() throws Exception {
        return reputationToken.totalSupply();
    }
} 