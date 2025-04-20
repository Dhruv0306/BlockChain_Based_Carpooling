package com.carpool.repository;

import com.carpool.model.Ride;
import com.carpool.model.RideRequest;
import com.carpool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for RideRequest entity
 * Handles all database operations related to ride requests
 */
@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    // Find requests by ride
    List<RideRequest> findByRide(Ride ride);
    
    // Find requests by accepter
    List<RideRequest> findByAccepter(User accepter);
    
    // Find requests by status
    List<RideRequest> findByStatus(RideRequest.RequestStatus status);
    
    // Find pending requests for a ride
    List<RideRequest> findByRideAndStatus(Ride ride, RideRequest.RequestStatus status);
    
    // Find accepted requests for a ride
    @Query("SELECT r FROM RideRequest r WHERE r.ride = :ride AND r.status = 'ACCEPTED'")
    List<RideRequest> findAcceptedRequestsForRide(@Param("ride") Ride ride);
    
    // Find requests with blockchain transaction ID
    List<RideRequest> findByBlockchainTransactionIdIsNotNull();
    
    // Count pending requests for a ride
    long countByRideAndStatus(Ride ride, RideRequest.RequestStatus status);
} 