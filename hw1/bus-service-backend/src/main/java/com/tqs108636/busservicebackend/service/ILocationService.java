package com.tqs108636.busservicebackend.service;

import java.util.List;
import java.util.Optional;

import com.tqs108636.busservicebackend.model.Location;

public interface ILocationService {
    public Optional<Location> findLocationById(Long id);

    public List<Location> findAll();

    public Optional<Location> findByName(String name);

    public List<Location> findConnectedLocations(Long connectedToId);
}
