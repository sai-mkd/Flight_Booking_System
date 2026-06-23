package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalUsers;
    private Long totalFlights;
    private Long totalBookings;
    private BigDecimal totalRevenue;
}