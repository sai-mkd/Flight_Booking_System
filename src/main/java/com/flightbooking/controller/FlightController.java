package com.flightbooking.controller;

import com.flightbooking.dto.FlightRequest;
import com.flightbooking.dto.FlightResponse;
import com.flightbooking.entity.*;
import com.flightbooking.enums.FlightStatus;
import com.flightbooking.exception.ResourceNotFoundException;
import com.flightbooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    @PostMapping
    public Flight createFlight(
            @RequestBody FlightRequest request) {

        Airline airline =
                airlineRepository.findById(request.getAirlineId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Airline not found"));

        Airport source =
                airportRepository.findById(request.getSourceAirportId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Source airport not found"));

        Airport destination =
                airportRepository.findById(request.getDestinationAirportId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Destination airport not found"));

        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airline(airline)
                .sourceAirport(source)
                .destinationAirport(destination)
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .durationMinutes(request.getDurationMinutes())
                .price(request.getPrice())
                .totalSeats(request.getAvailableSeats())
                .availableSeats(request.getAvailableSeats())
                .status(FlightStatus.SCHEDULED)
                .build();

        return flightRepository.save(flight);
    }

    @GetMapping
    public List<FlightResponse> getAllFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        return flightRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(flight -> FlightResponse.builder()
                        .id(flight.getId())
                        .flightNumber(flight.getFlightNumber())
                        .airline(flight.getAirline().getAirlineName())
                        .sourceAirport(flight.getSourceAirport().getAirportCode())
                        .destinationAirport(flight.getDestinationAirport().getAirportCode())
                        .departureTime(flight.getDepartureTime())
                        .arrivalTime(flight.getArrivalTime())
                        .availableSeats(flight.getAvailableSeats())
                        .price(flight.getPrice())
                        .build()
                )
                .toList();
    }
    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightRepository.deleteById(id);
        return "Flight deleted successfully";
    }
}