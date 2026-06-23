package com.flightbooking.controller;

import com.flightbooking.dto.AnalyticsResponse;
import com.flightbooking.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics() {
        return analyticsService.getAnalytics();
    }
}