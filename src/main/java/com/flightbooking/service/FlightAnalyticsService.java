package com.flightbooking.service;

import com.flightbooking.dto.FlightOccupancyResponse;
import com.flightbooking.entity.Flight;
import com.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightAnalyticsService {

    private final FlightRepository flightRepository;

    public List<FlightOccupancyResponse> getFlightOccupancy() {

        return flightRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    private FlightOccupancyResponse convert(Flight flight) {

        int totalSeats = flight.getTotalSeats() == null ? 0 : flight.getTotalSeats();
        int availableSeats = flight.getAvailableSeats() == null ? 0 : flight.getAvailableSeats();

        int bookedSeats = totalSeats - availableSeats;

        double occupancyRate =
                totalSeats == 0
                        ? 0.0
                        : (bookedSeats * 100.0) / totalSeats;

        return FlightOccupancyResponse.builder()
                .flightNumber(flight.getFlightNumber())
                .totalSeats(totalSeats)
                .availableSeats(availableSeats)
                .bookedSeats(bookedSeats)
                .occupancyRate(occupancyRate)
                .build();
    }
}