package com.carpool.BlockChain_Based_Carpooling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String walletAddress;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String drivingLicense;

    @Column(nullable = false)
    private boolean isDLValidated;

    @Column(nullable = false)
    private String userType; // "POSTER" or "ACCEPTER"

    @Column(nullable = false)
    private double reputation = 10.0;

    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL)
    private List<Ride> postedRides = new ArrayList<>();

    @OneToMany(mappedBy = "accepter", cascade = CascadeType.ALL)
    private List<Ride> acceptedRides = new ArrayList<>();

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
    private List<ReputationVote> givenVotes = new ArrayList<>();

    @OneToMany(mappedBy = "votedUser", cascade = CascadeType.ALL)
    private List<ReputationVote> receivedVotes = new ArrayList<>();
} 