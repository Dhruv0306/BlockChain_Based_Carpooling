package com.carpool.BlockChain_Based_Carpooling.controller;

import com.carpool.BlockChain_Based_Carpooling.model.Ride;
import com.carpool.BlockChain_Based_Carpooling.model.User;
import com.carpool.BlockChain_Based_Carpooling.service.RideService;
import com.carpool.BlockChain_Based_Carpooling.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final RideService rideService;
    private final UserService userService;

    public RideController(RideService rideService, UserService userService) {
        this.rideService = rideService;
        this.userService = userService;
    }

    @PostMapping("/post")
    public Ride postRide(@RequestParam String driverWallet,
                         @RequestParam String pickup,
                         @RequestParam String dropoff,
                         @RequestParam Double fare,
                         @RequestParam Integer seats) throws Exception {
        User driver = userService.findByWalletAddress(driverWallet);
        return rideService.postRide(driver, pickup, dropoff, fare, seats);
    }

    @GetMapping("/available")
    public List<Ride> getAvailableRides() {
        return rideService.getAvailableRides();
    }
}
