package com.carpool.Carpooling.service;

import com.carpool.Carpooling.repository.RideRepository;
import com.carpool.Carpooling.model.Ride;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public Ride postRide(String driverWallet, String pickup, String dropoff, BigInteger fare, BigInteger seats) throws Exception {
        Ride ride = new Ride(driverWallet, pickup, dropoff, fare, seats, false);
        rideRepository.save(ride);
        blockchainService.postRide(pickup, dropoff, fare, seats);
        return ride;
    }

    public List<Ride> getAvailableRides() {
        return rideRepository.findAll();
    }
}
