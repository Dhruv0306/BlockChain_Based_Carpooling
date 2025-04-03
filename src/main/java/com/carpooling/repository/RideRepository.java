package com.carpooling.repository;

import com.carpooling.model.Ride;
import com.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByPoster(User poster);
    List<Ride> findByIsActiveTrue();
    
    @Query(value = "SELECT r.* FROM rides r WHERE r.is_active = true AND r.departure_time > :now AND " +
           "ST_DWithin(r.start_point, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius)",
           nativeQuery = true)
    List<Ride> findNearbyRides(@Param("longitude") double longitude, 
                              @Param("latitude") double latitude, 
                              @Param("radius") double radius,
                              @Param("now") LocalDateTime now);
    
    List<Ride> findByIsActiveTrueAndDepartureTimeAfterOrderByDepartureTimeAsc(LocalDateTime now);
} 