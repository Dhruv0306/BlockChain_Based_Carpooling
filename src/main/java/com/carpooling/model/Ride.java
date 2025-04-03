package com.carpooling.model;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

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
    
    @Column(nullable = false)
    private String startLocation;
    
    @Column(nullable = false)
    private String endLocation;
    
    @Column(nullable = false)
    private LocalDateTime departureTime;
    
    @Column(nullable = false)
    private Integer availableSeats;
    
    @Column(nullable = false)
    private Double pricePerSeat;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point startPoint;
    
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point endPoint;
    
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
} 