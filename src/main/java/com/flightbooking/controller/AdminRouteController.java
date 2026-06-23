package com.flightbooking.controller;

import com.flightbooking.dto.RouteStatsResponse;
import com.flightbooking.service.RouteAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRouteController {

    private final RouteAnalyticsService routeAnalyticsService;

    @GetMapping("/top-routes")
    public List<RouteStatsResponse> getTopRoutes() {
        return routeAnalyticsService.getTopRoutes();
    }
}