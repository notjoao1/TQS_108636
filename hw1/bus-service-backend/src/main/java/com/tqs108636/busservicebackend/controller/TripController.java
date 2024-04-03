package com.tqs108636.busservicebackend.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.dto.TripDetailsDTO;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.service.ITripService;

@RestController
@RequestMapping("api/trips")
public class TripController {
    private ITripService tripService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public TripController(ITripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TripDetailsDTO> getTripDetails(@PathVariable("id") Long tripId) {
        logger.info("GET /api/trips/{}'", tripId);
        Optional<TripDetailsDTO> optionalTripDetails = tripService.getTripDetails(tripId);

        if (optionalTripDetails.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalTripDetails.get());
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getTrips(
            @RequestParam(name = "from") Optional<String> fromLocation,
            @RequestParam(name = "to") Optional<String> toLocation,
            @RequestParam(name = "upcoming") Optional<Boolean> upcoming) {
        logger.info("GET /api/trips. present params: from = {}; to = {}; upcoming = {}'", fromLocation.isPresent(),
                toLocation.isPresent(), upcoming.isPresent());

        // only one is empty but not both
        if (fromLocation.isEmpty() ^ toLocation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // both empty
        if (fromLocation.isEmpty() && toLocation.isEmpty())
            return ResponseEntity.ok(tripService.findAll());

        if (upcoming.isEmpty()) {
            List<Trip> tripsByRoute = tripService.findAllTripsByRoute(fromLocation.get(), toLocation.get());
            return ResponseEntity.ok(tripsByRoute);
        }

        List<Trip> upcomingTrips = tripService.findUpcomingTripsByRoute(fromLocation.get(), toLocation.get());

        return ResponseEntity.ok(upcomingTrips);
    }
}
