package com.tqs108636.busservicebackend.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private RouteRepository routeRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, RouteRepository routeRepository) {
        this.locationRepository = locationRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Location> findAll() {
        logger.debug("Finding all locations");
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findByName(String name) {
        logger.debug("Finding location by name - {}", name);
        return locationRepository.findByName(name);
    }

    @Override
    public List<Location> findConnectedLocations(String connectedToName) {
        logger.debug("Finding connected locations to - {}", connectedToName);

        Optional<Location> optLocation = this.findByName(connectedToName);

        if (optLocation.isEmpty()) {
            logger.debug("Location with name {} was not found.", connectedToName);
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
