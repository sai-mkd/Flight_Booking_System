package com.flightbooking.controller;

import com.flightbooking.dto.FlightOccupancyResponse;
import com.flightbooking.service.FlightAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminFlightAnalyticsController {

    private final FlightAnalyticsService flightAnalyticsService;

    @GetMapping("/flight-occupancy")
    public List<FlightOccupancyResponse> getFlightOccupancy() {

        return flightAnalyticsService.getFlightOccupancy();
    }
}