package com.flightbooking.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Long bookingId;

    private String paymentMethod;

    private BigDecimal amount;
}