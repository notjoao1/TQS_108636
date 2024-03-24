package com.tqs108636.busservicebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.repository.TripRepository;
import com.tqs108636.busservicebackend.service.impl.RouteService;
import com.tqs108636.busservicebackend.service.impl.TripService;

@ExtendWith(MockitoExtension.class)
class TripServiceMockRepoTest {
        @Mock
        TripRepository tripRepository;

        @Mock
        RouteService routeService;

        @InjectMocks
        TripService tripService;

        Route route1, route2, route3;
        Trip trip1, trip2, trip3, trip4, trip5, trip6, trip7;

        @BeforeEach
        void setup() {
                // route 1: Aveiro -> Porto -> Braga
                // route 2: Braga -> Porto
                route1 = new Route(1L, 70, null);
                route2 = new Route(2L, 30, null);
                route3 = new Route(3L, 60, null);

                final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

                // 6 trips for route1 - all except trip3 (5 of them are upcoming)
                // date order is: trip2 < trip1 < trip5 < trip6 < trip7 < trip4
                trip1 = new Trip(1L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10L, 0, ZoneOffset.UTC),
                                15.0f,
                                20);
                trip2 = new Trip(2L, route1,
                                LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS - 500L, 0, ZoneOffset.UTC), 12.0f,
                                20);
                trip3 = new Trip(3L, route2, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10L, 0, ZoneOffset.UTC),
                                8.0f,
                                20);
                trip4 = new Trip(4L, route1,
                                LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10000L, 0, ZoneOffset.UTC),
                                12.0f, 15);
                trip5 = new Trip(4L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 20L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
                trip6 = new Trip(4L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 30L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
                trip7 = new Trip(4L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 40L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
        }

        @Test
        void testFindTripsForRoute1() {
                when(tripRepository.findByRoute(route1))
                                .thenReturn(Arrays.asList(trip1, trip2, trip4, trip5, trip6, trip7));
                when(routeService.findById(route1.getId())).thenReturn(Optional.of(route1));

                List<Trip> tripsForRoute1 = tripService.findAllTripsByRoute(route1.getId());

                assertEquals(6, tripsForRoute1.size());
                assertTrue(tripsForRoute1.stream().allMatch((t) -> t.getRoute().getId() == route1.getId()));

                verify(tripRepository, times(1)).findByRoute(route1);
                verify(routeService, times(1)).findById(route1.getId());
        }

        @Test
        void testFindTripsForInvalidRoute() {
                when(routeService.findById(any())).thenReturn(Optional.empty());

                List<Trip> tripsForInvalidRoute = tripService.findAllTripsByRoute(null);

                assertTrue(tripsForInvalidRoute.isEmpty());
        }

        @Test
        void testFindUpcomingTripsForRoute1() {
                when(routeService.findById(route1.getId())).thenReturn(Optional.of(route1));
                when(tripRepository.findUpcomingTripsByRoute(route1))
                                .thenReturn(Arrays.asList(trip1, trip4, trip5, trip6, trip7));

                List<Trip> upcomingTrips = tripService.findUpcomingTripsByRoute(route1.getId());

                assertEquals(5, upcomingTrips.size());

                verify(routeService, times(1)).findById(route1.getId());
                verify(tripRepository, times(1)).findUpcomingTripsByRoute(route1);
        }

        @Test
        void testFindUpcomingTripsForInvalidRoute() {
                when(routeService.findById(route3.getId())).thenReturn(Optional.of(route3));
                when(tripRepository.findUpcomingTripsByRoute(route3)).thenReturn(new ArrayList<>());

                List<Trip> upcomingTrips = tripService.findUpcomingTripsByRoute(route3.getId());

                assertTrue(upcomingTrips.isEmpty());

                verify(routeService, times(1)).findById(route3.getId());
                verify(tripRepository, times(1)).findUpcomingTripsByRoute(route3);

        }
}
