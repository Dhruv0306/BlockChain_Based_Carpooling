// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PaymentEscrow {
    mapping(string => uint256) public ridePayments;
    mapping(string => bool) public paymentReleased;
    
    event PaymentDeposited(string rideId, address from, uint256 amount);
    event PaymentReleased(string rideId, address to, uint256 amount);
    event PaymentRefunded(string rideId, address to, uint256 amount);
    
    function depositPayment(string memory rideId) public payable {
        require(msg.value > 0, "Payment amount must be greater than 0");
        require(ridePayments[rideId] == 0, "Payment already deposited");
        
        ridePayments[rideId] = msg.value;
        emit PaymentDeposited(rideId, msg.sender, msg.value);
    }
    
    function releasePayment(string memory rideId, address payable to) public {
        require(ridePayments[rideId] > 0, "No payment to release");
        require(!paymentReleased[rideId], "Payment already released");
        
        uint256 amount = ridePayments[rideId];
        paymentReleased[rideId] = true;
        to.transfer(amount);
        emit PaymentReleased(rideId, to, amount);
    }
    
    function refundPayment(string memory rideId, address payable to) public {
        require(ridePayments[rideId] > 0, "No payment to refund");
        require(!paymentReleased[rideId], "Payment already released");
        
        uint256 amount = ridePayments[rideId];
        paymentReleased[rideId] = true;
        to.transfer(amount);
        emit PaymentRefunded(rideId, to, amount);
    }
} 