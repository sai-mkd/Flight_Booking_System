package com.flightbooking.repository;

import com.flightbooking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository
        extends JpaRepository<Airport, Long> {
}