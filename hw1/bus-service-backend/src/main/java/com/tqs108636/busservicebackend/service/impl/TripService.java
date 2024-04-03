package com.tqs108636.busservicebackend.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.dto.TripDetailsDTO;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.repository.ReservationRepository;
import com.tqs108636.busservicebackend.repository.TripRepository;
import com.tqs108636.busservicebackend.service.IRouteService;
import com.tqs108636.busservicebackend.service.ITripService;

@Service
public class TripService implements ITripService {
    private TripRepository tripRepository;

    private ReservationRepository reservationRepository;

    private IRouteService routeService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public TripService(TripRepository tripRepository, IRouteService routeService,
            ReservationRepository reservationRepository) {
        this.tripRepository = tripRepository;
        this.routeService = routeService;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Trip> findUpcomingTripsByRoute(String fromLocationName, String toLocationName) {
        logger.debug("findUpcomingTripsByRoute, from location = {}, to location = {}", fromLocationName,
                toLocationName);

        List<Route> routeList = routeService.findRouteFromLocationToLocation(fromLocationName, toLocationName);
        if (routeList.isEmpty())
            return new ArrayList<>();

        Set<Trip> upcomingTripsSet = new HashSet<>();
        for (Route route : routeList) {
            tripRepository.findUpcomingTripsByRoute(route).forEach(upcomingTripsSet::add);
        }

        return new ArrayList<>(upcomingTripsSet);
    }

    @Override
    public List<Trip> findAllTripsByRoute(String fromLocationName, String toLocationName) {
        logger.debug("findAllTripsByRoute, from location = {}, to location = {}", fromLocationName, toLocationName);

        List<Route> routeList = routeService.findRouteFromLocationToLocation(fromLocationName, toLocationName);
        if (routeList.isEmpty())
            return new ArrayList<>();

        Set<Trip> upcomingTripsSet = new HashSet<>();
        for (Route route : routeList) {
            tripRepository.findByRoute(route).forEach(upcomingTripsSet::add);
        }

        return new ArrayList<>(upcomingTripsSet);
    }

    @Override
    public List<Trip> findAll() {
        logger.debug("findAll Trips");

        return tripRepository.findAll();
    }

    @Override
    public Optional<TripDetailsDTO> getTripDetails(Long tripId) {
        logger.debug("getTripDetails, for trip ID = {}", tripId);

        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isEmpty()) {
            logger.debug("Trip with ID = {} was not found.", tripId);
            return Optional.empty();
        }

        Trip trip = optionalTrip.get();

        List<Integer> takenSeatNumbers = reservationRepository.findTakenSeatNumbersByTrip(trip);

        List<Integer> availableSeatNumbers = IntStream.range(0, trip.getNumberOfSeats())
                .filter(n -> !takenSeatNumbers.contains(n))
                .boxed()
                .collect(Collectors.toList());

        TripDetailsDTO tripDetailsDTO = new TripDetailsDTO(trip.getId(), trip.getRoute(), trip.getDepartureTime(),
                trip.getPriceEuro(), trip.getNumberOfSeats(), availableSeatNumbers);

        logger.debug("Available seat numbers for trip ID = {} -> {}", tripId, availableSeatNumbers);

        return Optional.of(tripDetailsDTO);
    }

}
