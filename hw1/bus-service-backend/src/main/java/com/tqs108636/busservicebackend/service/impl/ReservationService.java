package com.tqs108636.busservicebackend.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.repository.ReservationRepository;
import com.tqs108636.busservicebackend.repository.TripRepository;
import com.tqs108636.busservicebackend.service.IReservationService;

@Service
public class ReservationService implements IReservationService {
    private ReservationRepository reservationRepository;
    private TripRepository tripRepository;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Optional<Reservation> createReservation(ReservationDTO reservationDTO) {
        logger.debug("CreateReservation with reservationDTO - {}", reservationDTO);

        Optional<Trip> optionalTrip = tripRepository.findById(reservationDTO.getTripId());

        if (optionalTrip.isEmpty()) {
            logger.debug("No trip was found with ID = {}", reservationDTO.getTripId());
            return Optional.empty();

        }

        Trip requestedTrip = optionalTrip.get();
        Optional<Reservation> reservationWithSameSeatNumber = reservationRepository
                .findByTripAndSeatNumber(requestedTrip, reservationDTO.getSeatNumber());

        if (isBusTripFull(requestedTrip)) {
            logger.debug("Bus trip with ID {} is full, cannot create reservation.", reservationDTO.getTripId());
            return Optional.empty();
        }
        // cannot make reservation for taken seat number
        if (reservationWithSameSeatNumber.isPresent()) {
            logger.debug("Bus trip with ID {} is already has seat number {} occuppied, cannot create reservation.",
                    reservationDTO.getTripId(), reservationDTO.getSeatNumber());
            return Optional.empty();
        }
        if (!isSeatNumberValid(requestedTrip, reservationDTO.getSeatNumber())) {
            logger.debug("Requested seat number {} is not valid.", reservationDTO.getSeatNumber());
            return Optional.empty();
        }
        Reservation newReservation = reservationRepository
                .save(new Reservation(requestedTrip, reservationDTO.getSeatNumber(), reservationDTO.getClientName()));

        logger.debug("Successfully saved reservation - {}", newReservation);
        return Optional.of(newReservation);
    }

    @Override
    public Optional<Reservation> findReservationByUUID(UUID reservationToken) {
        logger.debug("findReservationByUUID, with UUID = {}", reservationToken);
        return reservationRepository.findById(reservationToken);
    }

    @Override
    public boolean isBusTripFull(Trip trip) {
        logger.debug("isBusTripFull, for trip with ID = {}", trip.getId());
        int takenSeats = reservationRepository.countByTrip(trip);
        return trip.getNumberOfSeats() == takenSeats;
    }

    @Override
    public boolean isSeatNumberValid(Trip trip, int seatNumber) {
        logger.debug("isSeatNumberValid, for trip with ID = {}; seatNumber = {}", trip.getId(), seatNumber);
        int totalSeats = trip.getNumberOfSeats();
        return seatNumber < totalSeats && seatNumber >= 0;
    }

}
