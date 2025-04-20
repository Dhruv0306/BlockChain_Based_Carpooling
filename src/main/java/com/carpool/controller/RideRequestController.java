package com.carpool.controller;

import com.carpool.model.RideRequest;
import com.carpool.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for ride request operations
 */
@RestController
@RequestMapping("/api/ride-requests")
public class RideRequestController {
    private final DatabaseService databaseService;

    @Autowired
    public RideRequestController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Create a new ride request
    @PostMapping
    public ResponseEntity<RideRequest> createRideRequest(@RequestBody RideRequest request) {
        RideRequest createdRequest = databaseService.createRideRequest(request);
        return ResponseEntity.ok(createdRequest);
    }

    // Get pending requests for a ride
    @GetMapping("/ride/{rideId}/pending")
    public ResponseEntity<List<RideRequest>> getPendingRequests(@PathVariable Long rideId) {
        try {
            List<RideRequest> requests = databaseService.findPendingRequestsForRide(rideId);
            return ResponseEntity.ok(requests);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update request status
    @PutMapping("/{requestId}/status")
    public ResponseEntity<RideRequest> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam RideRequest.RequestStatus status) {
        try {
            RideRequest updatedRequest = databaseService.updateRideRequestStatus(requestId, status);
            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Accept a ride request
    @PutMapping("/{requestId}/accept")
    public ResponseEntity<RideRequest> acceptRideRequest(@PathVariable Long requestId) {
        try {
            RideRequest acceptedRequest = databaseService.acceptRideRequest(requestId);
            return ResponseEntity.ok(acceptedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get user's ride requests
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RideRequest>> getUserRideRequests(@PathVariable Long userId) {
        try {
            List<RideRequest> requests = databaseService.findUserRideRequests(userId);
            return ResponseEntity.ok(requests);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 