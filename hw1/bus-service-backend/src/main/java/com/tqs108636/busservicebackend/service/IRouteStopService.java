package com.tqs108636.busservicebackend.service;

import java.util.List;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.RouteStop;

public interface IRouteStopService {
    List<RouteStop> findRouteStopByLocation(Location location);
}
