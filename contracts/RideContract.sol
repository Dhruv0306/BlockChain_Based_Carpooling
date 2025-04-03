// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract RideContract {
    struct Ride {
        address poster;
        uint256 seats;
        uint256 fare;
        string source;
        string destination;
        bool isActive;
        mapping(address => bool) accepters;
    }
    
    mapping(bytes32 => Ride) public rides;
    
    event RideCreated(bytes32 indexed rideId, address poster);
    event RideAccepted(bytes32 indexed rideId, address accepter);
    event RideCancelled(bytes32 indexed rideId);
    
    function createRide(
        bytes32 rideId,
        uint256 seats,
        uint256 fare,
        string memory source,
        string memory destination
    ) public {
        Ride storage ride = rides[rideId];
        ride.poster = msg.sender;
        ride.seats = seats;
        ride.fare = fare;
        ride.source = source;
        ride.destination = destination;
        ride.isActive = true;
        
        emit RideCreated(rideId, msg.sender);
    }
    
    function acceptRide(bytes32 rideId) public payable {
        Ride storage ride = rides[rideId];
        require(ride.isActive, "Ride is not active");
        require(msg.value >= ride.fare, "Insufficient payment");
        require(!ride.accepters[msg.sender], "Already accepted");
        
        ride.accepters[msg.sender] = true;
        ride.seats--;
        
        if (ride.seats == 0) {
            ride.isActive = false;
        }
        
        emit RideAccepted(rideId, msg.sender);
    }
    
    function cancelRide(bytes32 rideId) public {
        Ride storage ride = rides[rideId];
        require(ride.poster == msg.sender, "Only poster can cancel");
        require(ride.isActive, "Ride is not active");
        
        ride.isActive = false;
        emit RideCancelled(rideId);
    }
} 