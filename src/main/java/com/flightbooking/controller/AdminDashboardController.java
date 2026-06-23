package com.flightbooking.controller;

import com.flightbooking.dto.DashboardResponse;
import com.flightbooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    @GetMapping
    public DashboardResponse getDashboard() {

        Long totalUsers =
                userRepository.count();

        Long totalFlights =
                flightRepository.count();

        Long totalBookings =
                bookingRepository.count();

        BigDecimal revenue =
                bookingRepository.getTotalRevenue();

        return DashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalFlights(totalFlights)
                .totalBookings(totalBookings)
                .totalRevenue(revenue)
                .build();
    }
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }
}