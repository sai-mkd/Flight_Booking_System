package com.flightbooking.controller;

import com.flightbooking.dto.AirlineRequest;
import com.flightbooking.entity.Airline;
import com.flightbooking.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineRepository airlineRepository;

    @PostMapping
    public Airline createAirline(
            @RequestBody AirlineRequest request) {

        Airline airline = Airline.builder()
                .airlineCode(request.getAirlineCode())
                .airlineName(request.getAirlineName())
                .build();

        return airlineRepository.save(airline);
    }

    @GetMapping
    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }
}