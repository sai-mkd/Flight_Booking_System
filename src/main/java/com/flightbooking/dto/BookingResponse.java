package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;

    private String flightNumber;
    private String airline;
    private String pnr;

    private String sourceAirport;
    private String destinationAirport;

    private Integer numberOfSeats;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime bookingDate;
    
}