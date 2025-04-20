package com.carpool.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * User entity representing both Posters and Accepters in the system
 * Contains user information and role management
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "driving_license")
    private String drivingLicense;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "reputation_score")
    private double reputationScore = 10.0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum UserRole {
        POSTER,
        ACCEPTER,
        ADMIN
    }
} 