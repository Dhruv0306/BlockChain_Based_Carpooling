package com.carpooling.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class Web3Service {

    private final Web3j web3j;
    private final BigInteger gasPrice;
    private final BigInteger gasLimit;

    public Web3Service(Web3j web3j,
                      @Value("${web3j.contract-gas-price}") BigInteger gasPrice,
                      @Value("${web3j.contract-gas-limit}") BigInteger gasLimit) {
        this.web3j = web3j;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    public BigDecimal getBalance(String address) throws Exception {
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        return Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER);
    }

    public String verifySignature(String message, String signature, String address) throws Exception {
        // Verify that the signature was signed by the address owner
        byte[] messageHash = message.getBytes();
        byte[] signatureBytes = signature.getBytes();
        
        // Recover the signer's address from the signature
        String recoveredAddress = Keys.getAddress(ECKeyPair.create(signatureBytes));
        
        if (!recoveredAddress.equalsIgnoreCase(address)) {
            throw new Exception("Invalid signature");
        }
        
        return recoveredAddress;
    }

    public TransactionReceipt transferMatic(String fromAddress, String toAddress, BigDecimal amount) throws Exception {
        Credentials credentials = Credentials.create(fromAddress); // This should be the private key in production
        return Transfer.sendFunds(
            web3j, credentials, toAddress,
            amount, Convert.Unit.ETHER
        ).send();
    }
} 