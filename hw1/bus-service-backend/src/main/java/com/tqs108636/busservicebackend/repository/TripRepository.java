package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {
    @Query(value = "SELECT t FROM Trip t JOIN FETCH t.route r WHERE r = :requestedRoute AND t.departureTime > CURRENT_TIMESTAMP() ORDER BY t.departureTime")
    List<Trip> findUpcomingTripsByRoute(Route requestedRoute);

    List<Trip> findByRoute(Route requestedRoute);

}
