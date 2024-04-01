package com.tqs108636.busservicebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;

@DataJpaTest
class ReservationRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReservationRepository reservationRepository;

    Trip trip1, trip2, trip3;
    Route route1, route2;

    Reservation reservation1, reservation2, reservation3;

    @BeforeEach
    void setup() {
        route1 = new Route(0);
        trip1 = new Trip(route1, null, 0, 2); // 2 seats
        reservation1 = new Reservation(trip1, 0, "pessoaTest1");

        route2 = new Route(0);
        trip2 = new Trip(route2, null, 0, 100);

        trip3 = new Trip(route2, null, 0, 5);
        reservation2 = new Reservation(trip3, 2, "pessoaTest2");
        reservation3 = new Reservation(trip3, 4, "pessoaTest3");

        entityManager.persist(route1);
        entityManager.persist(trip1);
        entityManager.persist(reservation1);

        entityManager.persist(route2);
        entityManager.persist(trip2);

        entityManager.persist(trip3);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);

        entityManager.flush();
    }

    @Test
    void testCountReservations() {
        int nReservationsTrip1 = reservationRepository.countByTrip(trip1);

        assertEquals(1, nReservationsTrip1);

        int nReservationsTrip2 = reservationRepository.countByTrip(trip2);

        assertEquals(0, nReservationsTrip2);
    }

    @Test
    void testFindByTrip_AndSeatNumber_Exists() {
        Optional<Reservation> foundReservation = reservationRepository.findByTripAndSeatNumber(trip1, 0);

        assertTrue(foundReservation.isPresent());
        assertEquals(reservation1, foundReservation.get());
    }

    @Test
    void testFindByTrip_AndSeatNumber_DoesNotExist() {
        Optional<Reservation> foundReservation = reservationRepository.findByTripAndSeatNumber(trip2, 0);

        assertTrue(foundReservation.isEmpty());
    }

    @Test
    void testFindTakenSeatNumbers_ByTrip() {
        List<Integer> takenSeatNumbersTrip1 = reservationRepository.findTakenSeatNumbersByTrip(trip1);

        assertEquals(1, takenSeatNumbersTrip1.size());
        assertEquals(0, takenSeatNumbersTrip1.get(0));

        List<Integer> takenSeatNumbersTrip3 = reservationRepository.findTakenSeatNumbersByTrip(trip3);

        assertEquals(2, takenSeatNumbersTrip3.size());
        assertTrue(takenSeatNumbersTrip3.contains(2));
        assertTrue(takenSeatNumbersTrip3.contains(4));
    }
}
