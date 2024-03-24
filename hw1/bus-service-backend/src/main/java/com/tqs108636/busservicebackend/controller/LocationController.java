package com.tqs108636.busservicebackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.service.ILocationService;

@RestController
@RequestMapping("api/locations")
public class LocationController {
    ILocationService locationService;

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getLocations(
            @RequestParam(name = "connectedTo") Optional<String> locationName) {
        if (locationName.isEmpty()) {
            return ResponseEntity.ok(locationService.findAll());
        }
        List<Location> connectedLocations = locationService.findConnectedLocations(locationName.get());

        if (connectedLocations.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(connectedLocations);
    }

    @GetMapping("{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id) {
        Optional<Location> optLocation = locationService.findLocationById(id);
        if (optLocation.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optLocation.get());
    }
}
