package com.carpool.Carpooling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverWallet;
    private String pickup;
    private String dropoff;
    private BigInteger fare;
    private BigInteger seats;
    private boolean completed;

    public Ride(String driverWallet, String pickup, String dropoff, BigInteger fare, BigInteger seats, boolean completed) {
        this.driverWallet = driverWallet;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.fare = fare;
        this.seats = seats;
        this.completed = completed;
    }
}
