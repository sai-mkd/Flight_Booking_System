package com.flightbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/search-flights")
    public String searchFlightsPage() {
        return "search-flights";
    }
    @GetMapping("/book/{flightId}")
    public String bookFlightPage(
            @PathVariable Long flightId) {

        return "book-flight";
    }
    @GetMapping("/my-bookings")
    public String myBookingsPage() {
        return "my-bookings";
    }
}