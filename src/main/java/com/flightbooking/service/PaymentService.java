package com.flightbooking.service;

import com.flightbooking.dto.PaymentRequest;
import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Payment;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.enums.PaymentStatus;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public Payment processPayment(PaymentRequest request) {

        Booking existingBooking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();

        payment.setBooking(existingBooking);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());

        boolean success = Math.random() > 0.2;

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            existingBooking.setStatus(BookingStatus.CONFIRMED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            existingBooking.setStatus(BookingStatus.FAILED);
        }

        bookingRepository.save(existingBooking);
        return paymentRepository.save(payment);
    }
}