package com.tqs108636.busservicebackend.controller;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.everyItem;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.dto.TripDetailsDTO;
import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.service.impl.TripService;

@WebMvcTest(TripController.class)
class TripControllerMockServiceTest {
        @Autowired
        MockMvc mockMvc;

        @MockBean
        TripService tripService;

        final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

        Location locAveiro, locPorto, locBraga, locFaro;
        RouteStop rs1, rs2, rs3, rs4, rs5, rs6, rs7;
        Route route1, route2, route3;
        Trip trip1, trip2, trip3, trip4, trip5, trip6, trip7, trip8;
        TripDetailsDTO tripDetailsDTO1;

        @BeforeEach
        void setup() {
                // route 1: Aveiro -> Porto -> Braga
                // route 2: Braga -> Porto
                // route 3: Aveiro -> Braga
                route1 = new Route(1L, 70, null);
                route2 = new Route(2L, 30, null);
                route3 = new Route(3L, 60, null);

                locAveiro = new Location(1L, "Aveiro");
                locPorto = new Location(100L, "Porto");
                locBraga = new Location(10000L, "Braga");
                locFaro = new Location(100000L, "Faro");

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

                tripDetailsDTO1 = new TripDetailsDTO(1L, route1,
                                LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS + 10L, 0, ZoneOffset.UTC),
                                15.0f,
                                20, Arrays.asList(0, 1, 2, 3, 4));
        }

        @Test
        void testGetAllTrips_FromAveiro_ToBraga() throws Exception {
                when(tripService.findAllTripsByRoute(locAveiro.getName(), locBraga.getName()))
                                .thenReturn(Arrays.asList(trip1, trip2, trip4, trip5, trip6, trip7, trip8));

                mockMvc.perform(get("/api/trips?from=Aveiro&to=Braga")).andExpectAll(
                                status().isOk(),
                                jsonPath("$", hasSize(7)),
                                jsonPath("$[*].route.id",
                                                everyItem(anyOf(is(route1.getId().intValue()),
                                                                is(route3.getId().intValue())))));

                verify(tripService, times(1)).findAllTripsByRoute(locAveiro.getName(), locBraga.getName());
        }

        @Test
        void testGetAllTrips_FromFaro_ToPorto() throws Exception {
                when(tripService.findAllTripsByRoute(locFaro.getName(), locPorto.getName()))
                                .thenReturn(new ArrayList<>());

                mockMvc.perform(get("/api/trips?from=Faro&to=Porto")).andExpectAll(
                                status().isOk(),
                                jsonPath("$").isEmpty());

                verify(tripService, times(1)).findAllTripsByRoute(locFaro.getName(), locPorto.getName());
        }

        @Test
        void testGetUpcomingTrips_FromAveiro_ToBraga() throws Exception {
                when(tripService.findUpcomingTripsByRoute(locAveiro.getName(), locBraga.getName()))
                                .thenReturn(Arrays.asList(trip1, trip4, trip5, trip6, trip7));

                mockMvc.perform(get("/api/trips?from=Aveiro&to=Braga&upcoming=true")).andExpectAll(
                                status().isOk(),
                                jsonPath("$", hasSize(5)),
                                jsonPath("$[*].route.id",
                                                everyItem(anyOf(is(route1.getId().intValue()),
                                                                is(route3.getId().intValue())))));

                verify(tripService, times(1)).findUpcomingTripsByRoute(locAveiro.getName(), locBraga.getName());
        }

        @Test
        void testGetUpcomingTrips_FromAveiro_ToNull() throws Exception {
                mockMvc.perform(get("/api/trips?from=Aveiro")).andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$").doesNotExist());

                verify(tripService, times(0)).findAllTripsByRoute(anyString(), anyString());
        }

        @Test
        void testGetUpcomingTrips_FromNull_ToBraga() throws Exception {
                mockMvc.perform(get("/api/trips?to=Braga")).andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$").doesNotExist());

                verify(tripService, times(0)).findAllTripsByRoute(anyString(), anyString());
        }

        @Test
        void testGetTripDetails_ValidTrip() throws Exception {
                when(tripService.getTripDetails(anyLong())).thenReturn(Optional.of(tripDetailsDTO1));

                mockMvc.perform(get("/api/trips/1")).andExpectAll(
                                status().isOk(),
                                jsonPath("$.id").value(trip1.getId()),
                                jsonPath("$.availableSeatNumbers", hasSize(5)));

                verify(tripService, times(1)).getTripDetails(anyLong());
        }

        @Test
        void testGetTripDetails_InvalidTrip() throws Exception {
                when(tripService.getTripDetails(anyLong())).thenReturn(Optional.empty());

                mockMvc.perform(get("/api/trips/10000")).andExpectAll(
                                status().isNotFound(),
                                jsonPath("$").doesNotExist());

                verify(tripService, times(1)).getTripDetails(anyLong());
        }
}
