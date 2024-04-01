package com.tqs108636.busservicebackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Trip;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    int countByTrip(Trip trip);

    // to find if a reservation with this seatNumber, for this trip, has been made
    Optional<Reservation> findByTripAndSeatNumber(Trip trip, int seatNumber);

    @Query(value = "SELECT r.seatNumber FROM Reservation r JOIN r.trip t " +
            "WHERE t = :trip")
    List<Integer> findTakenSeatNumbersByTrip(Trip trip);
}
