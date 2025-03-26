package com.carpool.Carpooling.controller;

import com.carpool.Carpooling.model.Ride;
import com.carpool.Carpooling.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/post")
    public Ride postRide(@RequestParam String driverWallet,
                         @RequestParam String pickup,
                         @RequestParam String dropoff,
                         @RequestParam BigInteger fare,
                         @RequestParam BigInteger seats) throws Exception {
        return rideService.postRide(driverWallet, pickup, dropoff, fare, seats);
    }

    @GetMapping("/available")
    public List<Ride> getAvailableRides() {
        return rideService.getAvailableRides();
    }
}
