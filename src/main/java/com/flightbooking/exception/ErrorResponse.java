package com.flightbooking.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private int status;
}