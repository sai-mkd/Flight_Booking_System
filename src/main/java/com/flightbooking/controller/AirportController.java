package com.flightbooking.controller;

import com.flightbooking.dto.AirportRequest;
import com.flightbooking.entity.Airport;
import com.flightbooking.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportRepository airportRepository;

    @PostMapping
    public Airport createAirport(
            @RequestBody AirportRequest request) {

        Airport airport = Airport.builder()
                .airportCode(request.getAirportCode())
                .airportName(request.getAirportName())
                .city(request.getCity())
                .country(request.getCountry())
                .build();

        return airportRepository.save(airport);
    }

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
}