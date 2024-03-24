package com.tqs108636.busservicebackend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;
import com.tqs108636.busservicebackend.repository.LocationRepository;
import com.tqs108636.busservicebackend.repository.RouteRepository;
import com.tqs108636.busservicebackend.service.ILocationService;

@Service
public class LocationService implements ILocationService {
    private LocationRepository locationRepository;

    private RouteRepository routeRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, RouteRepository routeRepository) {
        this.locationRepository = locationRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public Optional<Location> findLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findByName(String name) {
        return locationRepository.findByName(name);
    }

    @Override
    public List<Location> findConnectedLocations(String connectedToName) {
        Optional<Location> optLocation = this.findByName(connectedToName);

        if (optLocation.isEmpty()) {
            return new ArrayList<>();
        }

        List<Route> locationStops = routeRepository.findByStopLocation(optLocation.get());

        Set<Location> connectedLocations = new HashSet<>();

        List<RouteStop> sameRouteStops;
        for (Route route : locationStops) {
            sameRouteStops = route.getRouteStops();
            sameRouteStops.stream().forEach(rs -> connectedLocations.add(rs.getLocation()));
        }

        // remove the asked for location
        connectedLocations.remove(optLocation.get());

        return new ArrayList<>(connectedLocations);
    }

}
