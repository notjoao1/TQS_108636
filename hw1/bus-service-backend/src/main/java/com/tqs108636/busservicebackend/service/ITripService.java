package com.tqs108636.busservicebackend.service;

import java.util.List;

import com.tqs108636.busservicebackend.model.Trip;

public interface ITripService {
    List<Trip> findUpcomingTripsByRoute(Long routeId);

    List<Trip> findAllTripsByRoute(Long routeId);

    List<Trip> findAll();
}
