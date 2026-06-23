package com.flightbooking.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PnrGeneratorService {

    public String generatePNR() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();
    }
}