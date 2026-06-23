package com.flightbooking.repository;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.User;
import com.flightbooking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPnr(String pnr);
    List<Booking> findByUser(User user);
    Long countByStatus(BookingStatus status);
    List<Booking> findByUserId(UUID userId);

    @Query("""
    SELECT b.flight.sourceAirport.airportCode,
           b.flight.destinationAirport.airportCode,
           COUNT(b),
           SUM(b.totalPrice)
    FROM Booking b
    WHERE b.status = 'CONFIRMED'
    GROUP BY b.flight.sourceAirport.airportCode,
             b.flight.destinationAirport.airportCode
    ORDER BY COUNT(b) DESC
""")
    List<Object[]> getTopRoutes();
    @Query("""
       SELECT COALESCE(SUM(b.totalPrice), 0)
       FROM Booking b
       WHERE b.status = 'CONFIRMED'
       """)
    java.math.BigDecimal getTotalRevenue();
}