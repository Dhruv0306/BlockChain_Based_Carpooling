// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

/**
 * @title CarpoolContract
 * @dev A smart contract for managing carpool rides on the blockchain
 * This contract handles ride creation, acceptance, completion, and payment tracking
 */
contract CarpoolContract {
    // Struct to store ride information
    struct Ride {
        string sourceLocation;      // Starting point of the ride
        string destinationLocation; // Ending point of the ride
        uint256 departureTime;      // Unix timestamp of departure
        uint256 availableSeats;     // Number of seats available
        uint256 farePerSeat;        // Fare per seat in smallest unit (e.g., wei)
        string status;             // Current status of the ride
        address driver;            // Address of the driver
        address[] passengers;      // Array of passenger addresses
    }

    // Mapping to store rides by their unique ID
    mapping(string => Ride) public rides;
    
    // Mapping to track payments for each ride
    mapping(string => mapping(address => bool)) public payments;

    // Events for tracking important state changes
    event RideCreated(string rideId, string source, string destination);
    event RideAccepted(string rideId, address passenger);
    event RideCompleted(string rideId);
    event PaymentRecorded(string rideId, address from, address to, uint256 amount);

    /**
     * @dev Creates a new ride
     * @param rideId Unique identifier for the ride
     * @param sourceLocation Starting point
     * @param destinationLocation Ending point
     * @param departureTime Unix timestamp
     * @param availableSeats Number of seats
     * @param farePerSeat Fare per seat
     */
    function createRide(
        string memory rideId,
        string memory sourceLocation,
        string memory destinationLocation,
        uint256 departureTime,
        uint256 availableSeats,
        uint256 farePerSeat
    ) public {
        require(availableSeats > 0, "Available seats must be greater than 0");
        require(farePerSeat > 0, "Fare per seat must be greater than 0");
        
        rides[rideId] = Ride({
            sourceLocation: sourceLocation,
            destinationLocation: destinationLocation,
            departureTime: departureTime,
            availableSeats: availableSeats,
            farePerSeat: farePerSeat,
            status: "OPEN",
            driver: msg.sender,
            passengers: new address[](0)
        });

        emit RideCreated(rideId, sourceLocation, destinationLocation);
    }

    /**
     * @dev Allows a passenger to accept a ride
     * @param rideId ID of the ride to accept
     */
    function acceptRide(string memory rideId) public {
        Ride storage ride = rides[rideId];
        require(keccak256(bytes(ride.status)) == keccak256(bytes("OPEN")), "Ride is not open");
        require(ride.availableSeats > 0, "No seats available");
        require(msg.sender != ride.driver, "Driver cannot accept their own ride");
        
        ride.passengers.push(msg.sender);
        ride.availableSeats--;
        
        if (ride.availableSeats == 0) {
            ride.status = "FULL";
        }
        
        emit RideAccepted(rideId, msg.sender);
    }

    /**
     * @dev Marks a ride as completed
     * @param rideId ID of the ride to complete
     */
    function completeRide(string memory rideId) public {
        Ride storage ride = rides[rideId];
        require(msg.sender == ride.driver, "Only driver can complete the ride");
        require(keccak256(bytes(ride.status)) != keccak256(bytes("COMPLETED")), "Ride already completed");
        
        ride.status = "COMPLETED";
        emit RideCompleted(rideId);
    }

    /**
     * @dev Records a payment for a ride
     * @param rideId ID of the ride
     * @param fromAddress Payer's address
     * @param toAddress Payee's address
     * @param amount Payment amount
     */
    function recordPayment(
        string memory rideId,
        address fromAddress,
        address toAddress,
        uint256 amount
    ) public {
        require(!payments[rideId][fromAddress], "Payment already recorded");
        require(amount > 0, "Amount must be greater than 0");
        
        payments[rideId][fromAddress] = true;
        emit PaymentRecorded(rideId, fromAddress, toAddress, amount);
    }

    /**
     * @dev Retrieves the current status of a ride
     * @param rideId ID of the ride
     * @return Current status
     */
    function getRideStatus(string memory rideId) public view returns (string memory) {
        return rides[rideId].status;
    }

    /**
     * @dev Retrieves basic details of a ride
     * @param rideId ID of the ride
     * @return Source and destination locations
     */
    function getRideDetails(string memory rideId) public view returns (string memory, string memory) {
        Ride memory ride = rides[rideId];
        return (ride.sourceLocation, ride.destinationLocation);
    }
} 