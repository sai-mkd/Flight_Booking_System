package com.flightbooking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightOccupancyResponse {

    private String flightNumber;

    private Integer totalSeats;

    private Integer availableSeats;

    private Integer bookedSeats;

    private Double occupancyRate;
}