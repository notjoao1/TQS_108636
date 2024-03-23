package com.tqs108636.busservicebackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.service.IRouteService;
import com.tqs108636.busservicebackend.service.ITripService;

@WebMvcTest
class TripControllerMockServiceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ITripService tripService;

    @MockBean
    IRouteService routeService;

    Route route1, route2, route3;
    Trip trip1, trip2, trip3, trip4;

    @BeforeEach
    void setup() {
        // route 1: Aveiro -> Porto -> Braga
        // route 2: Braga -> Porto
        route1 = new Route(1L, 70, null);
        route2 = new Route(2L, 30, null);
        route3 = new Route(3L, 60, null);

        trip1 = new Trip(1L, route1, new Date(1711449606L), 15.0f, 20);
        trip2 = new Trip(2L, route1, new Date(1679827206L), 12.0f, 20);
        trip3 = new Trip(3L, route2, new Date(1712140806L), 8.0f, 20);
        trip4 = new Trip(4L, route3, new Date(1722681606L), 12.0f, 15);
    }

    @Test
    void testGetAllTripsForRoute1() throws Exception {
        when(tripService.findAllTripsByRoute(route1.getId())).thenReturn(Arrays.asList(trip1, trip2));

        mockMvc.perform(get("/api/trips?route=1")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasSize(2)),
                jsonPath("$[0].id").value(route1.getId()),
                jsonPath("$[1].id").value(route2.getId()));

        verify(routeService, times(1)).findById(1L);
        verify(tripService, times(1)).findAllTripsByRoute(route1.getId());
    }

    @Test
    void testGetUpcomingTripsForRoute1() throws Exception {
        when(tripService.findUpcomingTripsByRoute(route1.getId())).thenReturn(Arrays.asList(trip1));

        mockMvc.perform(get("/api/trips?route=1&upcoming=true")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasSize(1)),
                jsonPath("$[0].id").value(trip1.getId()));

        verify(routeService, times(1)).findById(1L);
        verify(tripService, times(1)).findUpcomingTripsByRoute(route1.getId());
    }

    @Test
    void testGetAllTripsForInvalidRoute() throws Exception {
        when(routeService.findById(10000L)).thenReturn(Optional.empty());
        when(tripService.findAllTripsByRoute(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/trips?route=10000")).andExpectAll(
                status().isNotFound(),
                jsonPath("$").doesNotExist());

        verify(routeService, times(1)).findById(10000L);
        verify(tripService, times(0)).findAllTripsByRoute(any());
    }
}
