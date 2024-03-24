package com.tqs108636.busservicebackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.repository.LocationRepository;
import com.tqs108636.busservicebackend.repository.RouteRepository;
import com.tqs108636.busservicebackend.service.IRouteService;

@Service
public class RouteService implements IRouteService {
    private RouteRepository routeRepository;

    private LocationRepository locationRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public List<Route> findStartingWithLocation(String startingAtLocation) {
        Optional<Location> firstLocation = locationRepository.findByName(startingAtLocation);

        if (firstLocation.isEmpty()) {
            return new ArrayList<>();
        }

        return routeRepository.findRoutesByStartingLocation(firstLocation.get());
    }

    @Override
    public Optional<Route> findById(Long id) {
        return routeRepository.findById(id);
    }

}
