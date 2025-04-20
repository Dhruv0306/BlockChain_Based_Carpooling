// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract ReputationToken is ERC20, Ownable {
    mapping(address => uint256) public positiveRatings;
    mapping(address => uint256) public negativeRatings;
    
    event UserRated(address user, bool isPositive);
    
    constructor() ERC20("CarpoolReputation", "CPTR") {}
    
    function rateUser(address user, bool isPositive) public {
        require(user != msg.sender, "Cannot rate yourself");
        
        if (isPositive) {
            positiveRatings[user]++;
            _mint(user, 1 * 10**decimals());
        } else {
            negativeRatings[user]++;
            if (balanceOf(user) >= 1 * 10**decimals()) {
                _burn(user, 1 * 10**decimals());
            }
        }
        
        emit UserRated(user, isPositive);
    }
    
    function getReputationScore(address user) public view returns (uint256) {
        return balanceOf(user);
    }
} 