package com.tqs108636.busservicebackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.repository.TripRepository;
import com.tqs108636.busservicebackend.service.IRouteService;
import com.tqs108636.busservicebackend.service.ITripService;

@Service
public class TripService implements ITripService {
    private TripRepository tripRepository;

    private IRouteService routeService;

    @Autowired
    public TripService(TripRepository tripRepository, IRouteService routeService) {
        this.tripRepository = tripRepository;
        this.routeService = routeService;
    }

    @Override
    public List<Trip> findUpcomingTripsByRoute(Long routeId) {
        Optional<Route> optionalRoute = routeService.findById(routeId);
        if (optionalRoute.isEmpty())
            return new ArrayList<>();

        return tripRepository.findUpcomingTripsByRoute(optionalRoute.get());
    }

    @Override
    public List<Trip> findAllTripsByRoute(Long routeId) {
        Optional<Route> optionalRoute = routeService.findById(routeId);
        if (optionalRoute.isEmpty())
            return new ArrayList<>();

        return tripRepository.findByRoute(optionalRoute.get());
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

}
