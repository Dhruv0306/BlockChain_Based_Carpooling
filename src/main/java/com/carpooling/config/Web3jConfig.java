package com.carpooling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;

@Configuration
public class Web3jConfig {
    
    @Value("${web3j.client-address}")
    private String clientAddress;
    
    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress));
    }
    
    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(
            BigInteger.valueOf(20000000000L),  // gasPrice
            BigInteger.valueOf(6721975L)       // gasLimit
        );
    }
} 