package com.flightbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TicketController {

    @GetMapping("/ticket/{pnr}")
    public String ticketPage(@PathVariable String pnr) {
        return "ticket";
    }
}