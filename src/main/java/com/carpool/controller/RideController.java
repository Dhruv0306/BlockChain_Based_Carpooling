package com.carpool.controller;

import com.carpool.model.Ride;
import com.carpool.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for ride-related operations
 */
@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final DatabaseService databaseService;

    @Autowired
    public RideController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Create a new ride
    @PostMapping
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        Ride createdRide = databaseService.createRide(ride);
        return ResponseEntity.ok(createdRide);
    }

    // Search rides by location
    @GetMapping("/search/location")
    public ResponseEntity<List<Ride>> searchRidesByLocation(@RequestParam String location) {
        List<Ride> rides = databaseService.findRidesByLocation(location);
        return ResponseEntity.ok(rides);
    }

    // Search rides by time range
    @GetMapping("/search/time")
    public ResponseEntity<List<Ride>> searchRidesByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Ride> rides = databaseService.findRidesByTimeRange(start, end);
        return ResponseEntity.ok(rides);
    }

    // Search rides by fare range
    @GetMapping("/search/fare")
    public ResponseEntity<List<Ride>> searchRidesByFareRange(
            @RequestParam double minFare,
            @RequestParam double maxFare) {
        List<Ride> rides = databaseService.findRidesByFareRange(minFare, maxFare);
        return ResponseEntity.ok(rides);
    }

    // Update ride status
    @PutMapping("/{rideId}/status")
    public ResponseEntity<Ride> updateRideStatus(
            @PathVariable Long rideId,
            @RequestParam Ride.RideStatus status) {
        try {
            Ride updatedRide = databaseService.updateRideStatus(rideId, status);
            return ResponseEntity.ok(updatedRide);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get ride details
    @GetMapping("/{rideId}")
    public ResponseEntity<Ride> getRideDetails(@PathVariable Long rideId) {
        try {
            Ride ride = databaseService.findRideById(rideId);
            return ResponseEntity.ok(ride);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 