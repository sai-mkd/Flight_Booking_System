package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBookingResponse {

    private String pnr;

    private String flightNumber;

    private String airline;

    private String sourceAirport;

    private String destinationAirport;

    private Integer numberOfSeats;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime bookingDate;
}