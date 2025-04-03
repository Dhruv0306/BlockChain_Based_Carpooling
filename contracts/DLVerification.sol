// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract DLVerification {
    struct VerificationRecord {
        address userAddress;
        bytes32 dlHash;
        uint256 timestamp;
        bool isValid;
    }
    
    mapping(address => VerificationRecord) public verifications;
    
    event DLVerified(address indexed user, bytes32 dlHash);
    
    function verifyDL(bytes32 dlHash) public {
        verifications[msg.sender] = VerificationRecord({
            userAddress: msg.sender,
            dlHash: dlHash,
            timestamp: block.timestamp,
            isValid: true
        });
        
        emit DLVerified(msg.sender, dlHash);
    }
    
    function isVerified(address user) public view returns (bool) {
        return verifications[user].isValid;
    }
} 