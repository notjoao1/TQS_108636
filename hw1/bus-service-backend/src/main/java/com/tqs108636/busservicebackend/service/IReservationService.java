package com.tqs108636.busservicebackend.service;

import java.util.Optional;
import java.util.UUID;

import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Trip;

public interface IReservationService {
    Optional<Reservation> createReservation(ReservationDTO reservation);

    Optional<Reservation> findReservationByUUID(UUID reservationToken);

    boolean isBusTripFull(Trip trip);

    boolean isSeatNumberValid(Trip trip, int seatNumber);
}
