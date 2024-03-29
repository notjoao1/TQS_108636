package com.tqs108636.busservicebackend.service;

import java.util.List;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;

public interface ITripService {
    public List<Trip> getUpcomingTripsByRoute(Route route);
}
