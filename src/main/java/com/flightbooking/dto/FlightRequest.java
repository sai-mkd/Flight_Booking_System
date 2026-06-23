package com.flightbooking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightRequest {

    private String flightNumber;

    private Long airlineId;

    private Long sourceAirportId;

    private Long destinationAirportId;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer durationMinutes;

    private BigDecimal price;

    private Integer availableSeats;
}