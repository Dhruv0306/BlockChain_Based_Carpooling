package com.carpool.controller;

import com.carpool.model.User;
import com.carpool.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for user-related operations
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final DatabaseService databaseService;

    @Autowired
    public UserController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = databaseService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return databaseService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable User.UserRole role) {
        List<User> users = databaseService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    // Update user reputation
    @PutMapping("/{userId}/reputation")
    public ResponseEntity<User> updateReputation(
            @PathVariable Long userId,
            @RequestParam double newScore) {
        try {
            User updatedUser = databaseService.updateUserReputation(userId, newScore);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Verify driving license
    @PutMapping("/{userId}/verify")
    public ResponseEntity<User> verifyDrivingLicense(
            @PathVariable Long userId,
            @RequestParam String drivingLicense) {
        try {
            User user = databaseService.findUserByEmail(drivingLicense)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setVerified(true);
            user.setRole(User.UserRole.POSTER);
            User updatedUser = databaseService.createUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 