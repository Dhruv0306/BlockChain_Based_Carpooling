package com.carpooling.service;

import com.carpooling.model.Ride;
import com.carpooling.model.User;
import com.carpooling.model.UserRole;
import com.carpooling.repository.RideRepository;
import com.carpooling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideService {
    
    @Autowired
    private RideRepository rideRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public Ride createRide(Ride ride) {
        User poster = userRepository.findById(ride.getPoster().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (poster.getRole() != UserRole.POSTER) {
            throw new RuntimeException("User is not authorized to post rides");
        }
        
        ride.setPoster(poster);
        return rideRepository.save(ride);
    }
    
    public List<Ride> findNearbyRides(double longitude, double latitude, double radius) {
        return rideRepository.findNearbyRides(longitude, latitude, radius, LocalDateTime.now());
    }
    
    public List<Ride> findActiveRides() {
        return rideRepository.findByIsActiveTrue();
    }
    
    public List<Ride> findUpcomingRides() {
        return rideRepository.findByIsActiveTrueAndDepartureTimeAfterOrderByDepartureTimeAsc(LocalDateTime.now());
    }
    
    public List<Ride> findUserRides(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return rideRepository.findByPoster(user);
    }
    
    @Transactional
    public void cancelRide(Long rideId, Long userId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        
        if (!ride.getPoster().getId().equals(userId)) {
            throw new RuntimeException("Only the ride poster can cancel the ride");
        }
        
        ride.setIsActive(false);
        rideRepository.save(ride);
    }
} 