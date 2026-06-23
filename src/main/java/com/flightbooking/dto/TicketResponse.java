package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private String pnr;

    private String flightNumber;

    private String airline;

    private String sourceAirport;

    private String destinationAirport;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer numberOfSeats;

    private BigDecimal totalPrice;

    private String bookingStatus;

    private List<String> passengers;
}