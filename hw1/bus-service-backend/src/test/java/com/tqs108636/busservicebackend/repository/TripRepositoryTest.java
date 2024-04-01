package com.tqs108636.busservicebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;

@DataJpaTest
class TripRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TripRepository tripRepository;

    Route route1, route2, route3;
    Trip trip1, trip2, trip3, trip4, trip5, trip6, trip7;

    @BeforeEach
    void setup() {
        // route 1: Aveiro -> Porto -> Braga
        // route 2: Braga -> Porto
        route1 = new Route(70);
        route2 = new Route(30);
        route3 = new Route(60);

        final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

        // 6 trips for route1 - all except trip3 (5 of them are upcoming)
        // date order is: trip2 < trip1 < trip5 < trip6 < trip7 < trip4
        trip1 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10000L, 0, ZoneOffset.UTC), 15.0f,
                20);
        trip2 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS - 500000L, 0, ZoneOffset.UTC), 12.0f,
                20);
        trip3 = new Trip(route2, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 1000L, 0, ZoneOffset.UTC), 8.0f,
                20);
        trip4 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10000000L, 0, ZoneOffset.UTC),
                12.0f,
                15);
        trip5 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 20000L, 0, ZoneOffset.UTC), 12.0f,
                15);
        trip6 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 30000L, 0, ZoneOffset.UTC), 12.0f,
                15);
        trip7 = new Trip(route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 40000L, 0, ZoneOffset.UTC), 12.0f,
                15);

        entityManager.persist(route1);
        entityManager.persist(route2);
        entityManager.persist(route3);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.persist(trip4);
        entityManager.persist(trip5);
        entityManager.persist(trip6);
        entityManager.persist(trip7);
        entityManager.flush();

    }

    @Test
    void testFindUpcomingTripsByRoute1() {
        List<Trip> upcomingTrips = tripRepository.findUpcomingTripsByRoute(route1);

        assertEquals(5, upcomingTrips.size());

        List<Trip> sortedTrips = new ArrayList<>(upcomingTrips); // clone
        sortedTrips.sort(new TripComparator());

        assertEquals(sortedTrips, upcomingTrips); // upcoming trips should be sorted by default
        assertTrue(upcomingTrips.stream().allMatch((t) -> t.getRoute().getId() == route1.getId()));
        assertFalse(upcomingTrips.contains(trip2)); // trip2 happened in the past
    }

    @Test
    void testFindUpcomingTripsForInvalidRoute() {
        List<Trip> upcomingTrips = tripRepository.findUpcomingTripsByRoute(route3);

        assertTrue(upcomingTrips.isEmpty());
    }

    @Test
    void testFindTripsForRoute1() {
        List<Trip> allTripsByRoute1 = tripRepository.findByRoute(route1);

        assertEquals(6, allTripsByRoute1.size());

        assertTrue(allTripsByRoute1.stream().allMatch((t) -> t.getRoute().getId() == route1.getId()));
    }

    @Test
    void testFindTripsForInvalidRoute() {
        List<Trip> allTripsForRoute3 = tripRepository.findByRoute(route3);

        assertTrue(allTripsForRoute3.isEmpty());

    }

    class TripComparator implements Comparator<Trip> {

        @Override
        public int compare(Trip arg0, Trip arg1) {
            return arg0.getDepartureTime().compareTo(arg1.getDepartureTime());
        }

    }
}
