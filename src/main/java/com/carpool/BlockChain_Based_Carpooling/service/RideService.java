package com.carpool.BlockChain_Based_Carpooling.service;

import com.carpool.BlockChain_Based_Carpooling.model.Ride;
import com.carpool.BlockChain_Based_Carpooling.model.User;
import com.carpool.BlockChain_Based_Carpooling.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final BlockchainService blockchainService;

    public RideService(RideRepository rideRepository, BlockchainService blockchainService) {
        this.rideRepository = rideRepository;
        this.blockchainService = blockchainService;
    }

    public Ride postRide(User driver, String pickup, String dropoff, Double fare, Integer seats) throws Exception {
        // Create ride on blockchain first
        String rideId = blockchainService.createRideOnBlockchain(
            pickup,
            dropoff,
            LocalDateTime.now(),
            seats,
            fare
        );

        // Create ride in database
        Ride ride = new Ride();
        ride.setRideId(rideId);
        ride.setPoster(driver);
        ride.setSourceLocation(pickup);
        ride.setDestinationLocation(dropoff);
        ride.setDepartureTime(LocalDateTime.now());
        ride.setAvailableSeats(seats);
        ride.setFarePerSeat(fare);
        ride.setStatus("OPEN");

        return rideRepository.save(ride);
    }

    public List<Ride> getAvailableRides() {
        return rideRepository.findAll();
    }
}
