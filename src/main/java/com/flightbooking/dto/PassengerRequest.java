package com.flightbooking.dto;

import com.flightbooking.enums.PassengerGender;
import lombok.Data;

@Data
public class PassengerRequest {

    private String fullName;

    private Integer age;

    private PassengerGender gender;
}