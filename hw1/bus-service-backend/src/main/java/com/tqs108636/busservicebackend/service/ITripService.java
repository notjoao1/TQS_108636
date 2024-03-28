package com.tqs108636.busservicebackend.service;

import java.util.List;

import com.tqs108636.busservicebackend.model.Trip;

public interface ITripService {
    List<Trip> findUpcomingTripsByRoute(String fromLocationName, String toLocationName);

    List<Trip> findAllTripsByRoute(String fromLocationName, String toLocationName);

    List<Trip> findAll();
}
