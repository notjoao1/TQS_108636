package com.tqs108636.busservicebackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.model.Trip;

@RestController
@RequestMapping("api/trips")
public class TripController {
    @GetMapping
    public ResponseEntity<List<Trip>> getTrips(@RequestParam(name = "route") Long routeId,
            @RequestParam(name = "upcoming") boolean upcoming) {
        return null;
    }
}