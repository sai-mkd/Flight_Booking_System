package com.flightbooking.service;

import com.flightbooking.dto.RouteStatsResponse;
import com.flightbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteAnalyticsService {

    private final BookingRepository bookingRepository;

    public List<RouteStatsResponse> getTopRoutes() {

        List<Object[]> results = bookingRepository.getTopRoutes();

        return results.stream()
                .map(r -> RouteStatsResponse.builder()
                        .source((String) r[0])
                        .destination((String) r[1])
                        .totalBookings(((Number) r[2]).longValue())
                        .totalRevenue((BigDecimal) r[3])
                        .build())
                .toList();
    }
}