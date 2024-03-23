package com.tqs108636.busservicebackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.RouteStop;

public interface RouteStopRepository extends CrudRepository<RouteStop, Long> {
    List<RouteStop> findByLocation(Location location);
}
