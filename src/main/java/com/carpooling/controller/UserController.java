package com.carpooling.controller;

import com.carpooling.model.User;
import com.carpooling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            String token = userService.generateToken(registeredUser);
            return ResponseEntity.ok(Map.of(
                "user", registeredUser,
                "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/verify-dl/{userId}")
    public ResponseEntity<?> verifyDrivingLicense(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        try {
            String dlHash = request.get("dlHash");
            User verifiedUser = userService.verifyDrivingLicense(userId, dlHash);
            return ResponseEntity.ok(verifiedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/reputation/{userId}")
    public ResponseEntity<?> updateReputation(
            @PathVariable Long userId,
            @RequestBody Map<String, Boolean> request) {
        try {
            boolean isPositive = request.get("isPositive");
            userService.updateReputation(userId, isPositive);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 