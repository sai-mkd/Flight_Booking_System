package com.flightbooking.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome Admin";
    }
}