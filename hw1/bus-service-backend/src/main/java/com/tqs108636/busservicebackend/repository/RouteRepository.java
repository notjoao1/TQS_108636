package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;

public interface RouteRepository extends CrudRepository<Route, Long> {
    @Query(value = "SELECT r FROM Route r JOIN r.routeStops rs WHERE rs.location = :startingLocation AND rs.stopNumber = 0")
    List<Route> findRoutesByStartingLocation(Location startingLocation);
}
