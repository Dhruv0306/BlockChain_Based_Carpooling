package com.carpool.BlockChain_Based_Carpooling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3Config {

    @Value("${polygon.mumbai.rpc.url}")
    private String polygonMumbaiRpcUrl;

    @Value("${polygon.mumbai.contract.address}")
    private String contractAddress;

    @Value("${polygon.mumbai.private.key}")
    private String privateKey;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(polygonMumbaiRpcUrl));
    }

    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(
            BigInteger.valueOf(20000000000L), // Gas price
            BigInteger.valueOf(6721975L)      // Gas limit
        );
    }
} 