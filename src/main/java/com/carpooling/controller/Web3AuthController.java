package com.carpooling.controller;

import com.carpooling.service.Web3Service;
import com.carpooling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/web3")
@CrossOrigin(origins = "*") // For development only
public class Web3AuthController {

    @Autowired
    private Web3Service web3Service;

    @Autowired
    private UserService userService;

    private final Map<String, String> nonceStore = new HashMap<>();

    @GetMapping("/nonce/{address}")
    public ResponseEntity<Map<String, String>> getNonce(@PathVariable String address) {
        String nonce = UUID.randomUUID().toString();
        nonceStore.put(address, nonce);
        
        Map<String, String> response = new HashMap<>();
        response.put("nonce", nonce);
        response.put("message", "Please sign this nonce to verify your wallet: " + nonce);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifySignature(@RequestBody Map<String, String> request) {
        try {
            String address = request.get("address");
            String signature = request.get("signature");
            String nonce = nonceStore.get(address);
            
            if (nonce == null) {
                throw new Exception("No nonce found for this address");
            }
            
            String message = "Please sign this nonce to verify your wallet: " + nonce;
            String recoveredAddress = web3Service.verifySignature(message, signature, address);
            
            // If verification successful, generate JWT token
            String token = userService.generateTokenForAddress(address);
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("address", recoveredAddress);
            
            // Clean up nonce
            nonceStore.remove(address);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 