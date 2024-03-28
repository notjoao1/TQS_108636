package com.tqs108636.busservicebackend.service;

import java.util.List;
import java.util.Optional;

import com.tqs108636.busservicebackend.model.Route;

public interface IRouteService {
    List<Route> findAll();

    List<Route> findRouteFromLocationToLocation(String fromLocationName, String toLocationName);

    Optional<Route> findById(Long id);
}
