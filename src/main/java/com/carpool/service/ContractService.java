package com.carpool.service;

import com.carpool.config.ContractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;

@Service
public class ContractService {
    
    private final ContractConfig contractConfig;
    private final Web3j web3j;
    private final ContractGasProvider gasProvider;
    
    @Autowired
    public ContractService(ContractConfig contractConfig) {
        this.contractConfig = contractConfig;
        this.web3j = Web3j.build(new HttpService(contractConfig.getEthereumNetwork()));
        this.gasProvider = new StaticGasProvider(
            BigInteger.valueOf(contractConfig.getGasPrice()),
            BigInteger.valueOf(contractConfig.getGasLimit())
        );
    }
    
    public Web3j getWeb3j() {
        return web3j;
    }
    
    public ContractGasProvider getGasProvider() {
        return gasProvider;
    }
    
    public String getPrivateKey() {
        return contractConfig.getPrivateKey();
    }
    
    public String getAccountAddress() {
        return contractConfig.getAccountAddress();
    }
    
    public String getCarpoolContractAddress() {
        return contractConfig.getCarpoolContractAddress();
    }
    
    public String getPaymentContractAddress() {
        return contractConfig.getPaymentContractAddress();
    }
    
    public String getReputationContractAddress() {
        return contractConfig.getReputationContractAddress();
    }
    
    public String getInsuranceContractAddress() {
        return contractConfig.getInsuranceContractAddress();
    }
} 