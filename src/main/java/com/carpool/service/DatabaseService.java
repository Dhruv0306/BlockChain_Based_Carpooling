package com.carpool.service;

import com.carpool.model.*;
import com.carpool.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class to handle all database operations
 */
@Service
public class DatabaseService {
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;

    @Autowired
    public DatabaseService(
            UserRepository userRepository,
            RideRepository rideRepository,
            RideRequestRepository rideRequestRepository) {
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.rideRequestRepository = rideRequestRepository;
    }

    // User operations
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUsersByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public User updateUserReputation(Long userId, double newScore) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setReputationScore(newScore);
        return userRepository.save(user);
    }

    // Ride operations
    @Transactional
    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public List<Ride> findRidesByLocation(String location) {
        return rideRepository.findByStartLocationContainingOrEndLocationContaining(location, location);
    }

    public List<Ride> findRidesByTimeRange(LocalDateTime start, LocalDateTime end) {
        return rideRepository.findByDepartureTimeBetween(start, end);
    }

    public List<Ride> findRidesByFareRange(double minFare, double maxFare) {
        return rideRepository.findByFareRange(minFare, maxFare);
    }

    @Transactional
    public Ride updateRideStatus(Long rideId, Ride.RideStatus status) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setStatus(status);
        return rideRepository.save(ride);
    }

    // RideRequest operations
    @Transactional
    public RideRequest createRideRequest(RideRequest request) {
        return rideRequestRepository.save(request);
    }

    public List<RideRequest> findPendingRequestsForRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        return rideRequestRepository.findByRideAndStatus(ride, RideRequest.RequestStatus.PENDING);
    }

    @Transactional
    public RideRequest updateRideRequestStatus(Long requestId, RideRequest.RequestStatus status) {
        RideRequest request = rideRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Ride request not found"));
        request.setStatus(status);
        return rideRequestRepository.save(request);
    }

    public List<RideRequest> findUserRideRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return rideRequestRepository.findByAccepter(user);
    }

    // Combined operations
    @Transactional
    public RideRequest acceptRideRequest(Long requestId) {
        RideRequest request = rideRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Ride request not found"));
        
        // Update request status
        request.setStatus(RideRequest.RequestStatus.ACCEPTED);
        rideRequestRepository.save(request);
        
        // Update ride available seats
        Ride ride = request.getRide();
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        rideRepository.save(ride);
        
        return request;
    }

    // Find ride by ID
    public Ride findRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
    }
} 