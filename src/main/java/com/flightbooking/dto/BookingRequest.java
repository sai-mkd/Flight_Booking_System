package com.flightbooking.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {

    private Long flightId;

    private Integer numberOfSeats;
    private List<PassengerRequest> passengers;
}