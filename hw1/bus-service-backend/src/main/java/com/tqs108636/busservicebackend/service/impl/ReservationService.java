package com.tqs108636.busservicebackend.service.impl;

import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Optional<Reservation> createReservation(ReservationDTO reservationDTO) {
        Optional<Trip> optionalTrip = tripRepository.findById(reservationDTO.getTripId());

        if (optionalTrip.isEmpty())
            return Optional.empty();

        Trip requestedTrip = optionalTrip.get();
        Optional<Reservation> reservationWithSameSeatNumber = reservationRepository
                .findByTripAndSeatNumber(requestedTrip, reservationDTO.getSeatNumber());

        if (isBusTripFull(requestedTrip))
            return Optional.empty();

        // cannot make reservation for taken seat number
        if (reservationWithSameSeatNumber.isPresent())
            return Optional.empty();

        if (!isSeatNumberValid(requestedTrip, reservationDTO.getSeatNumber()))
            return Optional.empty();

        Reservation newReservation = reservationRepository
                .save(new Reservation(requestedTrip, reservationDTO.getSeatNumber(), reservationDTO.getClientName()));
        return Optional.of(newReservation);
    }

    @Override
    public Optional<Reservation> findReservationByUUID(UUID reservationToken) {
        return reservationRepository.findById(reservationToken);
    }

    @Override
    public boolean isBusTripFull(Trip trip) {
        int takenSeats = reservationRepository.countByTrip(trip);
        return trip.getNumberOfSeats() == takenSeats;
    }

    @Override
    public boolean isSeatNumberValid(Trip trip, int seatNumber) {
        int totalSeats = trip.getNumberOfSeats();
        return seatNumber < totalSeats && seatNumber >= 0;
    }

}
