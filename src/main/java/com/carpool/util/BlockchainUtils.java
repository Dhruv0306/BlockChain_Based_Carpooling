package com.carpool.util;

import org.web3j.utils.Convert;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility class for blockchain-related operations
 */
public class BlockchainUtils {
    
    /**
     * Convert Ether to Wei
     */
    public static BigInteger etherToWei(double ether) {
        return Convert.toWei(BigDecimal.valueOf(ether), Convert.Unit.ETHER).toBigInteger();
    }

    /**
     * Convert Wei to Ether
     */
    public static double weiToEther(BigInteger wei) {
        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER).doubleValue();
    }

    /**
     * Validate Ethereum address
     */
    public static boolean isValidAddress(String address) {
        return address != null && 
               address.matches("^0x[a-fA-F0-9]{40}$") &&
               !address.equals("0x0000000000000000000000000000000000000000");
    }

    /**
     * Convert timestamp to blockchain format
     */
    public static BigInteger timestampToBlockchain(long timestamp) {
        return BigInteger.valueOf(timestamp);
    }

    /**
     * Convert blockchain timestamp to Java timestamp
     */
    public static long blockchainToTimestamp(BigInteger blockchainTimestamp) {
        return blockchainTimestamp.longValue();
    }

    /**
     * Format transaction hash for display
     */
    public static String formatTransactionHash(String hash) {
        if (hash == null || hash.length() < 10) return hash;
        return hash.substring(0, 6) + "..." + hash.substring(hash.length() - 4);
    }
} 