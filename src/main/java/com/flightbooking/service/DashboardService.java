package com.flightbooking.service;
import com.flightbooking.dto.AnalyticsResponse;
import com.flightbooking.dto.DashboardResponse;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.UserRepository;
import com.flightbooking.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final AnalyticsService analyticsService;

    public DashboardResponse getDashboard() {

        AnalyticsResponse analytics = analyticsService.getAnalytics();

        return DashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalFlights(flightRepository.count())
                .totalBookings(bookingRepository.count())
                .totalRevenue(analytics.getTotalRevenue())
                .build();
    }
}