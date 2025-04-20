package com.carpool.repository;

import com.carpool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Handles all database operations related to users
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by email
    Optional<User> findByEmail(String email);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Find users by role
    List<User> findByRole(User.UserRole role);
    
    // Find verified users
    List<User> findByIsVerifiedTrue();
    
    // Find users with reputation score above threshold
    List<User> findByReputationScoreGreaterThan(double score);
} 