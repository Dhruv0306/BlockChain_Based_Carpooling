// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract RideInsurance {
    struct Insurance {
        uint256 premium;
        uint256 coverage;
        bool active;
        bool claimed;
    }
    
    mapping(string => Insurance) public rideInsurance;
    
    event InsurancePurchased(string rideId, uint256 premium, uint256 coverage);
    event ClaimFiled(string rideId, address claimant, uint256 amount);
    
    function purchaseInsurance(string memory rideId) public payable {
        require(msg.value > 0, "Premium must be greater than 0");
        require(!rideInsurance[rideId].active, "Insurance already exists");
        
        uint256 coverage = msg.value * 3; // 3x coverage of premium
        rideInsurance[rideId] = Insurance(msg.value, coverage, true, false);
        
        emit InsurancePurchased(rideId, msg.value, coverage);
    }
    
    function fileClaim(string memory rideId) public {
        require(rideInsurance[rideId].active, "No active insurance");
        require(!rideInsurance[rideId].claimed, "Already claimed");
        
        rideInsurance[rideId].claimed = true;
        payable(msg.sender).transfer(rideInsurance[rideId].coverage);
        
        emit ClaimFiled(rideId, msg.sender, rideInsurance[rideId].coverage);
    }
} 