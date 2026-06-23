package com.flightbooking.controller;

import com.flightbooking.dto.PaymentRequest;
import com.flightbooking.entity.Payment;
import com.flightbooking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public Payment processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}