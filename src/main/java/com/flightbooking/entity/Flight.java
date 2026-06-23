package com.flightbooking.entity;

import com.flightbooking.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "source_airport_id")
    private Airport sourceAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id")
    private Airport destinationAirport;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer durationMinutes;

    private BigDecimal price;

    private Integer availableSeats;

    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;
}