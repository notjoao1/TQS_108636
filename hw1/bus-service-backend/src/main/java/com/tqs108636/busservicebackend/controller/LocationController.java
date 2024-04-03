package com.tqs108636.busservicebackend.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.service.ILocationService;

@RestController
@RequestMapping("api/locations")
public class LocationController {
    private ILocationService locationService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getLocations(
            @RequestParam(name = "connectedTo") Optional<String> locationName) {
        logger.info("GET /api/locations. params: 'connectedTo = {}'", locationName.isPresent());
        if (locationName.isEmpty()) {
            return ResponseEntity.ok(locationService.findAll());
        }
        List<Location> connectedLocations = locationService.findConnectedLocations(locationName.get());

        if (connectedLocations.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(connectedLocations);
    }
}
