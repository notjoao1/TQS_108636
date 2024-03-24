package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query(value = "SELECT t FROM Trip t JOIN t.route r WHERE r = :requestedRoute AND t.departureTime > CURRENT_TIMESTAMP ORDER BY t.departureTime")
    List<Trip> findUpcomingTripsByRoute(Route requestedRoute);

    List<Trip> findByRoute(Route requestedRoute);

}
