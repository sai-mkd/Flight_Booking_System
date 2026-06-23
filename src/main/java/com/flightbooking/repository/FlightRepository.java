package com.flightbooking.repository;

import com.flightbooking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FlightRepository
        extends JpaRepository<Flight, Long> {

    @Query("""
SELECT f FROM Flight f
WHERE f.sourceAirport.airportCode = :source
AND f.destinationAirport.airportCode = :destination
AND f.price BETWEEN :minPrice AND :maxPrice
""")
    List<Flight> searchFlightsAdvanced(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
    Optional<Flight> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Flight> findWithLockingById(Long id);
}