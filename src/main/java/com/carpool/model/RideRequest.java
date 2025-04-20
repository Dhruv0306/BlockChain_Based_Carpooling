package com.carpool.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * RideRequest entity representing a request from an Accepter to join a ride
 * Contains request status and related information
 */
@Data
@Entity
@Table(name = "ride_requests")
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "accepter_id", nullable = false)
    private User accepter;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "blockchain_tx_id")
    private String blockchainTransactionId;

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

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELLED
    }
} 