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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/locations")
public class LocationController {
    private ILocationService locationService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Retrieves locations", description = "Fetches all locations or locations connected to a specified location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Location.class)))),
            @ApiResponse(responseCode = "404", description = "No connected locations found")
    })
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
