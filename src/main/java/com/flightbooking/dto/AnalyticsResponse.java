package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private Long totalBookings;
    private Long confirmedBookings;
    private Long cancelledBookings;
    private Long failedBookings;

    private BigDecimal totalRevenue;

    private Double successRate;
}