package com.flightbooking.dto;

import lombok.Data;

@Data
public class AirportRequest {

    private String airportCode;
    private String airportName;
    private String city;
    private String country;
}