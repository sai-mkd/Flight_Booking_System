package com.flightbooking.service;

import com.flightbooking.dto.LoginRequest;
import com.flightbooking.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    String login(LoginRequest request);
}