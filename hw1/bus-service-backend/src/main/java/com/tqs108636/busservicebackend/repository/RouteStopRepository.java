package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.RouteStop;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
    List<RouteStop> findByLocation(Location location);
}
