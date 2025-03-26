// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract CarpoolContract {
    struct Ride {
        string sourceLocation;
        string destinationLocation;
        uint256 departureTime;
        uint256 availableSeats;
        uint256 farePerSeat;
        address poster;
        address accepter;
        bool completed;
        bool exists;
    }

    mapping(string => Ride) public rides;
    string[] public rideIds;

    event RideCreated(string rideId, address poster);
    event RideAccepted(string rideId, address accepter);
    event RideCompleted(string rideId);
    event PaymentRecorded(string rideId, address from, address to, uint256 amount);

    function createRide(
        string memory rideId,
        string memory sourceLocation,
        string memory destinationLocation,
        uint256 departureTime,
        uint256 availableSeats,
        uint256 farePerSeat
    ) public {
        require(!rides[rideId].exists, "Ride already exists");
        
        rides[rideId] = Ride({
            sourceLocation: sourceLocation,
            destinationLocation: destinationLocation,
            departureTime: departureTime,
            availableSeats: availableSeats,
            farePerSeat: farePerSeat,
            poster: msg.sender,
            accepter: address(0),
            completed: false,
            exists: true
        });
        
        rideIds.push(rideId);
        emit RideCreated(rideId, msg.sender);
    }

    function acceptRide(string memory rideId) public {
        require(rides[rideId].exists, "Ride does not exist");
        require(rides[rideId].accepter == address(0), "Ride already accepted");
        require(rides[rideId].poster != msg.sender, "Cannot accept your own ride");
        
        rides[rideId].accepter = msg.sender;
        emit RideAccepted(rideId, msg.sender);
    }

    function completeRide(string memory rideId) public {
        require(rides[rideId].exists, "Ride does not exist");
        require(rides[rideId].accepter != address(0), "Ride not accepted");
        require(!rides[rideId].completed, "Ride already completed");
        require(msg.sender == rides[rideId].poster || msg.sender == rides[rideId].accepter, "Not authorized");
        
        rides[rideId].completed = true;
        emit RideCompleted(rideId);
    }

    function recordPayment(
        string memory rideId,
        address from,
        address to,
        uint256 amount
    ) public {
        require(rides[rideId].exists, "Ride does not exist");
        emit PaymentRecorded(rideId, from, to, amount);
    }

    function getRideStatus(string memory rideId) public view returns (string memory) {
        require(rides[rideId].exists, "Ride does not exist");
        if (!rides[rideId].completed) {
            if (rides[rideId].accepter == address(0)) {
                return "OPEN";
            } else {
                return "ACCEPTED";
            }
        } else {
            return "COMPLETED";
        }
    }

    function getRideDetails(string memory rideId) public view returns (string memory, string memory) {
        require(rides[rideId].exists, "Ride does not exist");
        return (rides[rideId].sourceLocation, rides[rideId].destinationLocation);
    }
} 