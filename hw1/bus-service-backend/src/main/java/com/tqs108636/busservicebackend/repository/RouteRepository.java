package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query(value = "SELECT r FROM Route r JOIN r.routeStops rs WHERE rs.location = :startingLocation AND rs.stopNumber = 0")
    List<Route> findRoutesByStartingLocation(Location startingLocation);

    @Query(value = "SELECT r FROM Route r JOIN r.routeStops rs WHERE rs.location = :stopLocation")
    List<Route> findByStopLocation(Location stopLocation); // routes that have a stop at "stopLocation"
}
