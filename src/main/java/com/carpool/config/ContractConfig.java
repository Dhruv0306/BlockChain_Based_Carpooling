package com.carpool.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ContractConfig {
    
    private final Dotenv dotenv;
    
    @Autowired
    public ContractConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }
    
    public String getEthereumNetwork() {
        return dotenv.get("ETHEREUM_NETWORK");
    }
    
    public String getCarpoolContractAddress() {
        return dotenv.get("CARPOOL_CONTRACT_ADDRESS");
    }
    
    public String getPaymentContractAddress() {
        return dotenv.get("PAYMENT_CONTRACT_ADDRESS");
    }
    
    public String getReputationContractAddress() {
        return dotenv.get("REPUTATION_CONTRACT_ADDRESS");
    }
    
    public String getInsuranceContractAddress() {
        return dotenv.get("INSURANCE_CONTRACT_ADDRESS");
    }
    
    public String getPrivateKey() {
        return dotenv.get("PRIVATE_KEY");
    }
    
    public String getAccountAddress() {
        return dotenv.get("ACCOUNT_ADDRESS");
    }
    
    public Long getChainId() {
        return Long.parseLong(dotenv.get("CHAIN_ID"));
    }
    
    public Long getGasPrice() {
        return Long.parseLong(dotenv.get("GAS_PRICE"));
    }
    
    public Long getGasLimit() {
        return Long.parseLong(dotenv.get("GAS_LIMIT"));
    }
} 