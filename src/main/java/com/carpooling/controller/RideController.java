package com.carpooling.controller;

import com.carpooling.model.Ride;
import com.carpooling.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    
    @Autowired
    private RideService rideService;
    
    @PostMapping
    public ResponseEntity<?> createRide(@RequestBody Ride ride) {
        try {
            Ride createdRide = rideService.createRide(ride);
            return ResponseEntity.ok(createdRide);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/nearby")
    public ResponseEntity<List<Ride>> findNearbyRides(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius) {
        return ResponseEntity.ok(rideService.findNearbyRides(longitude, latitude, radius));
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Ride>> findActiveRides() {
        return ResponseEntity.ok(rideService.findActiveRides());
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<Ride>> findUpcomingRides() {
        return ResponseEntity.ok(rideService.findUpcomingRides());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ride>> findUserRides(@PathVariable Long userId) {
        return ResponseEntity.ok(rideService.findUserRides(userId));
    }
    
    @PostMapping("/{rideId}/cancel")
    public ResponseEntity<?> cancelRide(
            @PathVariable Long rideId,
            @RequestParam Long userId) {
        try {
            rideService.cancelRide(rideId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 