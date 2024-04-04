package com.tqs108636.busservicebackend.service;

import java.util.List;
import java.util.Optional;

import com.tqs108636.busservicebackend.dto.TripDTO;
import com.tqs108636.busservicebackend.dto.TripDetailsDTO;

public interface ITripService {
    List<TripDTO> findUpcomingTripsByRoute(String fromLocationName, String toLocationName, String targetCurrency);

    List<TripDTO> findAllTripsByRoute(String fromLocationName, String toLocationName, String targetCurrency);

    List<TripDTO> findAll(String targetCurrency);

    Optional<TripDetailsDTO> getTripDetails(Long tripId);
}
