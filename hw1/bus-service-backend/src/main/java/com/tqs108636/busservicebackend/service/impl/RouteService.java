package com.tqs108636.busservicebackend.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Route> findAll() {
        logger.debug("Find all routes");
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findById(Long id) {
        logger.debug("Find route by ID = {}", id);
        return routeRepository.findById(id);
    }

    @Override
    public List<Route> findRouteFromLocationToLocation(String fromLocationName, String toLocationName) {
        logger.debug("Find route from location = {}; to location = {}", fromLocationName, toLocationName);

        Optional<Location> fromLocation = locationRepository.findByName(fromLocationName);
        Optional<Location> toLocation = locationRepository.findByName(toLocationName);

        if (fromLocation.isEmpty() || toLocation.isEmpty()) {
            logger.debug(
                    "Failed to find routes from location = {}, to location = {}. Present: fromLocation = {}, toLocation = {};",
                    fromLocationName, toLocationName, fromLocation.isPresent(), toLocation.isPresent());
            return new ArrayList<>();
        }
        return routeRepository.findRoutesFromLocationToLocation(fromLocation.get(), toLocation.get());
    }

}
