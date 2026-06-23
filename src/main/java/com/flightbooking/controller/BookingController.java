package com.flightbooking.controller;

import com.flightbooking.dto.BookingRequest;
import com.flightbooking.dto.BookingResponse;
import com.flightbooking.dto.MyBookingResponse;
import com.flightbooking.dto.TicketResponse;
import com.flightbooking.entity.*;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.exception.BadRequestException;
import com.flightbooking.exception.ResourceNotFoundException;
import com.flightbooking.repository.*;
import com.flightbooking.security.JwtService;
import com.flightbooking.service.PnrGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final PnrGeneratorService pnrGeneratorService;
    private final JwtService jwtService;

    @PostMapping
    @Transactional
    public BookingResponse createBooking(
            @RequestBody BookingRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Flight flight =
                flightRepository.findWithLockingById(
                                request.getFlightId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Flight not found"));

        if (flight.getAvailableSeats()
                < request.getNumberOfSeats()) {

            throw new BadRequestException(
                    "Not enough seats available");
        }
        if (request.getPassengers().size()
                != request.getNumberOfSeats()) {

            throw new BadRequestException(
                    "Passenger count must match seat count");
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats()
                        - request.getNumberOfSeats());

        flightRepository.save(flight);

        BigDecimal totalPrice =
                flight.getPrice().multiply(
                        BigDecimal.valueOf(
                                request.getNumberOfSeats()));


        Booking booking = Booking.builder()
                .user(user)
                .flight(flight)
                .numberOfSeats(
                        request.getNumberOfSeats())
                .totalPrice(totalPrice)
                .bookingDate(LocalDateTime.now())
                .pnr(pnrGeneratorService.generatePNR())
                .status(BookingStatus.PENDING)
                .build();

        Booking savedBooking =
                bookingRepository.save(booking);
        List<Passenger> passengers =
                request.getPassengers()
                        .stream()
                        .map(p -> Passenger.builder()
                                .fullName(p.getFullName())
                                .age(p.getAge())
                                .gender(p.getGender())
                                .booking(savedBooking)
                                .build())
                        .toList();

        passengerRepository.saveAll(passengers);

        return BookingResponse.builder()
                .bookingId(savedBooking.getId())
                .flightNumber(savedBooking.getFlight().getFlightNumber())
                .airline(savedBooking.getFlight().getAirline().getAirlineName())
                .sourceAirport(savedBooking.getFlight().getSourceAirport().getAirportCode())
                .destinationAirport(savedBooking.getFlight().getDestinationAirport().getAirportCode())
                .numberOfSeats(savedBooking.getNumberOfSeats())
                .totalPrice(savedBooking.getTotalPrice())
                .pnr(savedBooking.getPnr())
                .status(savedBooking.getStatus().name())
                .bookingDate(savedBooking.getBookingDate())
                .build();
    }
    private User getCurrentUser(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        String token = authHeader.substring(7);

        String email = jwtService.extractUsername(token);

        return userRepository.findByEmail(email)
                .orElseThrow();
    }
    @PostMapping("/cancel/{pnr}")
    public String cancelBooking(
            @PathVariable String pnr,
            HttpServletRequest request) {

        User user = getCurrentUser(request);

        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // ensure user owns booking
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // check status
        if (booking.getStatus().name().equals("CANCELLED")) {
            throw new RuntimeException("Booking already cancelled");
        }

        // restore seats
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(
                flight.getAvailableSeats() + booking.getNumberOfSeats()
        );

        // cancel booking
        booking.setStatus(BookingStatus.CANCELLED);

        flightRepository.save(flight);
        bookingRepository.save(booking);

        return "Booking cancelled successfully. PNR: " + pnr;
    }
    @GetMapping
    public List<BookingResponse> getMyBookings(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(b -> BookingResponse.builder()
                        .bookingId(b.getId())
                        .flightNumber(b.getFlight().getFlightNumber())
                        .airline(b.getFlight().getAirline().getAirlineName())
                        .sourceAirport(b.getFlight().getSourceAirport().getAirportCode())
                        .destinationAirport(b.getFlight().getDestinationAirport().getAirportCode())
                        .numberOfSeats(b.getNumberOfSeats())
                        .totalPrice(b.getTotalPrice())
                        .status(b.getStatus().name())
                        .bookingDate(b.getBookingDate())
                        .build()
                )
                .toList();
    }
    @GetMapping("/pnr/{pnr}")
    public TicketResponse getTicketByPnr(
            @PathVariable String pnr) {

        Booking booking =
                bookingRepository.findByPnr(pnr)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"));

        List<String> passengers =
                booking.getPassengers()
                        .stream()
                        .map(Passenger::getFullName)
                        .toList();

        return TicketResponse.builder()
                .pnr(booking.getPnr())
                .flightNumber(
                        booking.getFlight().getFlightNumber())
                .airline(
                        booking.getFlight()
                                .getAirline()
                                .getAirlineName())
                .sourceAirport(
                        booking.getFlight()
                                .getSourceAirport()
                                .getAirportCode())
                .destinationAirport(
                        booking.getFlight()
                                .getDestinationAirport()
                                .getAirportCode())
                .departureTime(
                        booking.getFlight()
                                .getDepartureTime())
                .arrivalTime(
                        booking.getFlight()
                                .getArrivalTime())
                .numberOfSeats(
                        booking.getNumberOfSeats())
                .totalPrice(
                        booking.getTotalPrice())
                .bookingStatus(
                        booking.getStatus().name())
                .passengers(passengers)
                .build();
    }
    @GetMapping("/my-bookings")
    public List<MyBookingResponse> getMyBookings(
            HttpServletRequest request) {

        User user = getCurrentUser(request);

        List<Booking> bookings =
                bookingRepository.findByUser(user);

        return bookings.stream()
                .map(b -> MyBookingResponse.builder()
                        .pnr(b.getPnr())
                        .flightNumber(b.getFlight().getFlightNumber())
                        .airline(b.getFlight().getAirline().getAirlineName())
                        .sourceAirport(b.getFlight().getSourceAirport().getAirportCode())
                        .destinationAirport(b.getFlight().getDestinationAirport().getAirportCode())
                        .numberOfSeats(b.getNumberOfSeats())
                        .totalPrice(b.getTotalPrice())
                        .status(b.getStatus().name())
                        .bookingDate(b.getBookingDate())
                        .build())
                .toList();
    }
    @PutMapping("/{bookingId}/cancel")
    public Booking cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight not found"));


        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not allowed");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        Flight flight = booking.getFlight();

        flight.setAvailableSeats(
                flight.getAvailableSeats() + booking.getNumberOfSeats()
        );

        flightRepository.save(flight);

        booking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }
}