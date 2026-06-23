package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String airline;

    private String sourceAirport;
    private String destinationAirport;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer availableSeats;
    private BigDecimal price;
}