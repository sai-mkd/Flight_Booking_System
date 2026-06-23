package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteStatsResponse {

    private String source;

    private String destination;

    private Long totalBookings;

    private BigDecimal totalRevenue;
}