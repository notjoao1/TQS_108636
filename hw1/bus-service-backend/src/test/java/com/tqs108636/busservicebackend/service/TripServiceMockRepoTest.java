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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;
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

        Location locAveiro, locPorto, locBraga, locFaro;
        RouteStop rs1, rs2, rs3, rs4, rs5, rs6, rs7;
        Route route1, route2, route3;
        Trip trip1, trip2, trip3, trip4, trip5, trip6, trip7, trip8;

        final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

        @BeforeEach
        void setup() {
                // route 1: Aveiro -> Porto -> Braga
                // route 2: Braga -> Porto
                // route 3: Aveiro -> Braga
                route1 = new Route(1L, 70, null);
                route2 = new Route(2L, 30, null);
                route3 = new Route(3L, 60, null);

                locAveiro = new Location(1L, "Aveiro", null);
                locPorto = new Location(100L, "Porto", null);
                locBraga = new Location(10000L, "Braga", null);
                locFaro = new Location(100000L, "Faro", null);

                rs1 = new RouteStop(1L, locAveiro, route1, 0, 0);
                rs2 = new RouteStop(2L, locPorto, route1, 1, 40);
                rs3 = new RouteStop(3L, locBraga, route1, 2, 30);
                route1.setRouteStops(Arrays.asList(rs1, rs2, rs3));

                rs4 = new RouteStop(4L, locPorto, route2, 0, 0);
                rs5 = new RouteStop(5L, locBraga, route2, 1, 30);
                route2.setRouteStops(Arrays.asList(rs4, rs5));

                rs6 = new RouteStop(6L, locAveiro, route3, 0, 0);
                rs7 = new RouteStop(7L, locBraga, route3, 1, 60);

                route3.setRouteStops(Arrays.asList(rs6, rs7));

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
                trip5 = new Trip(5L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 20L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
                trip6 = new Trip(6L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 30L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
                trip7 = new Trip(7L, route1, LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 40L, 0, ZoneOffset.UTC),
                                12.0f,
                                15);
                trip8 = new Trip(8L, route3,
                                LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS - 500L, 0, ZoneOffset.UTC), 20.0f, 20);
        }

        @Test
        void testFindTrips_FromAveiro_ToBraga() {
                when(routeService.findRouteFromLocationToLocation(locAveiro.getName(), locBraga.getName()))
                                .thenReturn(Arrays.asList(route1, route3));
                when(tripRepository.findByRoute(route1))
                                .thenReturn(Arrays.asList(trip1, trip2, trip4, trip5, trip6, trip7));
                when(tripRepository.findByRoute(route3))
                                .thenReturn(Arrays.asList(trip8));

                List<Trip> trips = tripService.findAllTripsByRoute(locAveiro.getName(), locBraga.getName());

                assertEquals(7, trips.size());
                assertTrue(trips.stream().allMatch((t) -> t.getRoute().getId() == route1.getId()
                                || t.getRoute().getId() == route3.getId()));

                verify(tripRepository, times(1)).findByRoute(route1);
                verify(tripRepository, times(1)).findByRoute(route3);
                verify(routeService, times(1)).findRouteFromLocationToLocation(locAveiro.getName(), locBraga.getName());
        }

        @Test
        void testFindTrips_FromInvalidRoute() {
                when(routeService.findRouteFromLocationToLocation(locFaro.getName(), locPorto.getName()))
                                .thenReturn(new ArrayList<>());

                List<Trip> tripsForInvalidRoute = tripService.findAllTripsByRoute(locFaro.getName(),
                                locPorto.getName());

                assertTrue(tripsForInvalidRoute.isEmpty());

                verify(routeService, times(1)).findRouteFromLocationToLocation(locFaro.getName(), locPorto.getName());
                verify(tripRepository, times(0)).findByRoute(any());
        }

        @Test
        void testFindUpcomingTrips_FromAveiro_ToBraga() {
                when(routeService.findRouteFromLocationToLocation(locAveiro.getName(), locBraga.getName()))
                                .thenReturn(Arrays.asList(route1, route3));
                when(tripRepository.findUpcomingTripsByRoute(route1))
                                .thenReturn(Arrays.asList(trip1, trip4, trip5, trip6, trip7));
                when(tripRepository.findUpcomingTripsByRoute(route3))
                                .thenReturn(new ArrayList<>());

                List<Trip> upcomingTrips = tripService.findUpcomingTripsByRoute(locAveiro.getName(),
                                locBraga.getName());

                assertEquals(5, upcomingTrips.size());
                assertTrue(upcomingTrips.stream().allMatch(t -> t.getDepartureTime().compareTo(LocalDateTime
                                .ofEpochSecond(CURRENT_TIME_SECONDS, 0, ZoneOffset.UTC)) > 0));

                verify(routeService, times(1)).findRouteFromLocationToLocation(locAveiro.getName(), locBraga.getName());
                verify(tripRepository, times(1)).findUpcomingTripsByRoute(route1);
        }

        @Test
        void testFindUpcomingTrips_FromFaro_ToPorto() {
                when(routeService.findRouteFromLocationToLocation(locFaro.getName(), locPorto.getName()))
                                .thenReturn(new ArrayList<>());

                List<Trip> upcomingTrips = tripService.findUpcomingTripsByRoute(locFaro.getName(), locPorto.getName());

                assertTrue(upcomingTrips.isEmpty());

                verify(routeService, times(1)).findRouteFromLocationToLocation(locFaro.getName(), locPorto.getName());
                verify(tripRepository, times(0)).findUpcomingTripsByRoute(any());

        }
}
