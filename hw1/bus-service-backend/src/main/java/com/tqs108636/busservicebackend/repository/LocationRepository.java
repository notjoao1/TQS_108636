package com.tqs108636.busservicebackend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tqs108636.busservicebackend.model.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByName(String name);
}
