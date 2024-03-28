package com.tqs108636.busservicebackend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Trip> findUpcomingTripsByRoute(String fromLocationName, String toLocationName) {
        List<Route> routeList = routeService.findRouteFromLocationToLocation(fromLocationName, toLocationName);
        if (routeList.isEmpty())
            return new ArrayList<>();

        Set<Trip> upcomingTripsSet = new HashSet<>();
        for (Route route : routeList) {
            tripRepository.findUpcomingTripsByRoute(route).forEach(upcomingTripsSet::add);
        }

        return new ArrayList<>(upcomingTripsSet);
    }

    @Override
    public List<Trip> findAllTripsByRoute(String fromLocationName, String toLocationName) {
        List<Route> routeList = routeService.findRouteFromLocationToLocation(fromLocationName, toLocationName);
        if (routeList.isEmpty())
            return new ArrayList<>();

        Set<Trip> upcomingTripsSet = new HashSet<>();
        for (Route route : routeList) {
            tripRepository.findByRoute(route).forEach(upcomingTripsSet::add);
        }

        return new ArrayList<>(upcomingTripsSet);
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

}
