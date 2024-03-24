package com.tqs108636.busservicebackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.service.IRouteService;

@RestController
@RequestMapping("api/routes")
public class RouteController {
    IRouteService routeService;

    @Autowired
    public RouteController(IRouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes(
            @RequestParam(name = "startingAt", required = false) Optional<String> startingAtLocation) {
        if (startingAtLocation.isEmpty())
            return ResponseEntity.ok(routeService.findAll());
        List<Route> startingAtLocationRoutes = routeService.findStartingWithLocation(startingAtLocation.get());

        if (startingAtLocationRoutes.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(startingAtLocationRoutes);
    }
}
