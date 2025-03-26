package com.carpool.Carpooling.repository;

import com.carpool.Carpooling.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
}
