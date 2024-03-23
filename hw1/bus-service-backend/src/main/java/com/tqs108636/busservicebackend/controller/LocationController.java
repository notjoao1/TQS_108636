package com.tqs108636.busservicebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.model.Location;

@RestController
@RequestMapping("api/location")
public class LocationController {
    @GetMapping
    public ResponseEntity<Location> getLocations(@RequestParam(name = "connectedTo") String locationName) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id) {
        return null;
    }
}
