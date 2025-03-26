package com.carpool.BlockChain_Based_Carpooling.service;

import com.carpool.BlockChain_Based_Carpooling.model.User;
import com.carpool.BlockChain_Based_Carpooling.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByWalletAddress(String walletAddress) {
        return userRepository.findByWalletAddress(walletAddress)
            .orElseThrow(() -> new RuntimeException("User not found with wallet address: " + walletAddress));
    }
} 