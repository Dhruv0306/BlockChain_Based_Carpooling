package com.carpooling.service;

import com.carpooling.model.User;
import com.carpooling.model.UserRole;
import com.carpooling.repository.UserRepository;
import com.carpooling.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        if (userRepository.existsByWalletAddress(user.getWalletAddress())) {
            throw new RuntimeException("Wallet address already registered");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    @Transactional
    public User verifyDrivingLicense(Long userId, String dlHash) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setDlVerificationHash(dlHash);
        user.setIsDLVerified(true);
        user.setRole(UserRole.POSTER);
        
        return userRepository.save(user);
    }
    
    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }
    
    public String generateTokenForAddress(String address) {
        User user = userRepository.findByWalletAddress(address)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setWalletAddress(address);
                    newUser.setRole(UserRole.ACCEPTER);
                    return userRepository.save(newUser);
                });
        return generateToken(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findByWalletAddress(String walletAddress) {
        return userRepository.findByWalletAddress(walletAddress);
    }
    
    @Transactional
    public void updateReputation(Long userId, boolean isPositive) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        double change = isPositive ? 0.25 : -0.25;
        user.setReputation(user.getReputation() + change);
        
        userRepository.save(user);
    }
} 