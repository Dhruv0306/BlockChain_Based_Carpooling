package com.carpool.repository;

import com.carpool.model.Ride;
import com.carpool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Ride entity
 * Handles all database operations related to rides
 */
@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    // Find rides by poster
    List<Ride> findByPoster(User poster);
    
    // Find rides by status
    List<Ride> findByStatus(Ride.RideStatus status);
    
    // Find rides by location
    List<Ride> findByStartLocationContainingOrEndLocationContaining(String startLocation, String endLocation);
    
    // Find rides by departure time range
    List<Ride> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
    
    // Find rides with available seats
    List<Ride> findByAvailableSeatsGreaterThan(int seats);
    
    // Find rides by fare range
    @Query("SELECT r FROM Ride r WHERE r.fare BETWEEN :minFare AND :maxFare")
    List<Ride> findByFareRange(@Param("minFare") double minFare, @Param("maxFare") double maxFare);
    
    // Find rides by poster's reputation score
    @Query("SELECT r FROM Ride r WHERE r.poster.reputationScore >= :minReputation")
    List<Ride> findByPosterReputation(@Param("minReputation") double minReputation);
} 