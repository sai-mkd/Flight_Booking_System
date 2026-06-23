package com.flightbooking.controller;

import com.flightbooking.dto.FlightResponse;
import com.flightbooking.entity.Flight;
import com.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightRepository flightRepository;

    @GetMapping("/search")
    public List<FlightResponse> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice
    ) {

        return flightRepository.searchFlightsAdvanced(
                        source,
                        destination,
                        minPrice,
                        maxPrice)
                .stream()
                .map(flight -> FlightResponse.builder()
                        .id(flight.getId())
                        .flightNumber(flight.getFlightNumber())
                        .airline(flight.getAirline().getAirlineName())
                        .sourceAirport(flight.getSourceAirport().getAirportCode())
                        .destinationAirport(flight.getDestinationAirport().getAirportCode())
                        .price(flight.getPrice())
                        .build()
                )
                .toList();
    }
    @GetMapping("/{id}")
    public FlightResponse getFlightById(@PathVariable Long id) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Flight not found"));

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline().getAirlineName())
                .sourceAirport(flight.getSourceAirport().getAirportCode())
                .destinationAirport(flight.getDestinationAirport().getAirportCode())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .availableSeats(flight.getAvailableSeats())
                .price(flight.getPrice())
                .build();
    }
}