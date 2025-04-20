// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract CarpoolContract {
    struct RideInfo {
        string rideId;
        address posterAddress;
        string startLocation;
        string endLocation;
        uint256 departureTime;
        uint256 availableSeats;
        uint256 fare;
        uint256 status; // 0: Available, 1: In Progress, 2: Completed
    }

    mapping(string => RideInfo) public rides;
    mapping(string => mapping(address => bool)) public rideRequests;
    mapping(string => mapping(address => bool)) public acceptedRequests;
    mapping(address => uint256) public userReputations;

    event RideCreated(string rideId, address posterAddress);
    event RideRequested(string rideId, address requester);
    event RideAccepted(string rideId, address accepter);
    event PaymentMade(string rideId, address from, address to, uint256 amount);
    event ReputationUpdated(address user, bool isPositive);

    function createRide(
        string memory rideId,
        string memory startLocation,
        string memory endLocation,
        uint256 departureTime,
        uint256 availableSeats,
        uint256 fare
    ) public {
        require(rides[rideId].posterAddress == address(0), "Ride already exists");
        
        rides[rideId] = RideInfo(
            rideId,
            msg.sender,
            startLocation,
            endLocation,
            departureTime,
            availableSeats,
            fare,
            0
        );
        
        emit RideCreated(rideId, msg.sender);
    }

    function requestRide(string memory rideId) public {
        require(rides[rideId].posterAddress != address(0), "Ride does not exist");
        require(rides[rideId].status == 0, "Ride not available");
        require(rides[rideId].availableSeats > 0, "No seats available");
        require(!rideRequests[rideId][msg.sender], "Already requested");
        
        rideRequests[rideId][msg.sender] = true;
        emit RideRequested(rideId, msg.sender);
    }

    function acceptRideRequest(string memory rideId, address requester) public {
        require(rides[rideId].posterAddress == msg.sender, "Not ride poster");
        require(rideRequests[rideId][requester], "No such request");
        require(!acceptedRequests[rideId][requester], "Already accepted");
        require(rides[rideId].availableSeats > 0, "No seats available");
        
        acceptedRequests[rideId][requester] = true;
        rides[rideId].availableSeats--;
        emit RideAccepted(rideId, requester);
    }

    function makePayment(string memory rideId) public payable {
        require(acceptedRequests[rideId][msg.sender], "Not accepted for ride");
        require(msg.value == rides[rideId].fare, "Incorrect payment amount");
        
        payable(rides[rideId].posterAddress).transfer(msg.value);
        emit PaymentMade(rideId, msg.sender, rides[rideId].posterAddress, msg.value);
    }

    function updateReputation(address user, bool isPositive) public {
        require(msg.sender != user, "Cannot rate yourself");
        
        if (isPositive) {
            userReputations[user]++;
        } else {
            if (userReputations[user] > 0) {
                userReputations[user]--;
            }
        }
        
        emit ReputationUpdated(user, isPositive);
    }

    function getRideInfo(string memory rideId) public view returns (RideInfo memory) {
        require(rides[rideId].posterAddress != address(0), "Ride does not exist");
        return rides[rideId];
    }

    function isRideRequestAccepted(string memory rideId, address requester) public view returns (bool) {
        return acceptedRequests[rideId][requester];
    }

    function getReputation(address user) public view returns (uint256) {
        return userReputations[user];
    }
} 