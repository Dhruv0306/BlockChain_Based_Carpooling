package com.carpool.service;

import com.carpool.config.ContractConfig;
import com.carpool.contract.PaymentEscrow;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;
import org.web3j.crypto.Credentials;

@Service
public class PaymentEscrowService extends ContractService {
    
    private final PaymentEscrow paymentEscrow;

    public PaymentEscrowService(ContractConfig contractConfig) {
        super(contractConfig);
        ContractGasProvider gasProvider = getGasProvider();
        Credentials credentials = Credentials.create(getPrivateKey());
        this.paymentEscrow = PaymentEscrow.load(
            getPaymentContractAddress(),
            getWeb3j(),
            credentials,
            gasProvider
        );
    }

    public PaymentEscrow getContract() {
        return paymentEscrow;
    }

    // Add methods for contract interactions
    public RemoteFunctionCall<TransactionReceipt> depositPayment(
        String rideId,
        BigInteger weiValue
    ) throws Exception {
        return paymentEscrow.depositPayment(rideId, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> releasePayment(
        String rideId,
        String to
    ) throws Exception {
        return paymentEscrow.releasePayment(rideId, to);
    }

    public RemoteFunctionCall<TransactionReceipt> refundPayment(
        String rideId,
        String to
    ) throws Exception {
        return paymentEscrow.refundPayment(rideId, to);
    }

    public RemoteFunctionCall<BigInteger> ridePayments(
        String rideId
    ) throws Exception {
        return paymentEscrow.ridePayments(rideId);
    }
} 