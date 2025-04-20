package com.carpool.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Ride entity representing a carpool ride posted by a Poster
 * Contains ride details and status information
 */
@Data
@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poster_id", nullable = false)
    private User poster;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "end_location", nullable = false)
    private String endLocation;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "fare", nullable = false)
    private double fare;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RideStatus status = RideStatus.OPEN;

    @Column(name = "blockchain_ride_id")
    private String blockchainRideId;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    private List<RideRequest> rideRequests;

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

    public enum RideStatus {
        OPEN,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
} 