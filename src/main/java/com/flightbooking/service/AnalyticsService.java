package com.flightbooking.service;

import com.flightbooking.dto.AnalyticsResponse;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final BookingRepository bookingRepository;

    public AnalyticsResponse getAnalytics() {

        long total = bookingRepository.count();

        long confirmed = bookingRepository.countByStatus(BookingStatus.CONFIRMED);
        long cancelled = bookingRepository.countByStatus(BookingStatus.CANCELLED);
        long failed = bookingRepository.countByStatus(BookingStatus.FAILED);

        BigDecimal revenue = bookingRepository.getTotalRevenue();

        double successRate = total == 0
                ? 0.0
                : (confirmed * 100.0) / total;

        return AnalyticsResponse.builder()
                .totalBookings(total)
                .confirmedBookings(confirmed)
                .cancelledBookings(cancelled)
                .failedBookings(failed)
                .totalRevenue(revenue == null ? BigDecimal.ZERO : revenue)
                .successRate(successRate)
                .build();
    }
}