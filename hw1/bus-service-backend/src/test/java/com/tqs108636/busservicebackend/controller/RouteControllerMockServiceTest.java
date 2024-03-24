package com.tqs108636.busservicebackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;
import com.tqs108636.busservicebackend.service.IRouteService;

@WebMvcTest(RouteController.class)
class RouteControllerMockServiceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    IRouteService routeService;

    Location locAveiro, locPorto, locBraga, locFaro;
    RouteStop rs1, rs2, rs3, rs4, rs5, rs6, rs7;
    Route route1, route2, route3;

    @BeforeEach
    void setup() {
        // route 1: Aveiro -> Porto -> Braga
        // route 2: Braga -> Porto
        route1 = new Route(1L, 70, null);
        route2 = new Route(2L, 30, null);
        route3 = new Route(3L, 60, null);

        locAveiro = new Location(1L, "Aveiro", null);
        locPorto = new Location(100L, "Porto", null);
        locBraga = new Location(10000L, "Braga", null);
        locFaro = new Location(100000L, "Faro", null);

        rs1 = new RouteStop(locAveiro, route1, 0, 0);
        rs2 = new RouteStop(locPorto, route1, 1, 40);
        rs3 = new RouteStop(locBraga, route1, 2, 30);
        route1.setRouteStops(Arrays.asList(rs1, rs2, rs3));

        rs4 = new RouteStop(locPorto, route2, 0, 0);
        rs5 = new RouteStop(locBraga, route2, 1, 30);
        route2.setRouteStops(Arrays.asList(rs4, rs5));

        rs6 = new RouteStop(locAveiro, route3, 0, 0);
        rs7 = new RouteStop(locBraga, route3, 1, 60);

        route3.setRouteStops(Arrays.asList(rs6, rs7));
    }

    @Test
    void testGetAllRoutes() throws Exception {
        when(routeService.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        mockMvc.perform(get("/api/routes")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasSize(3)),
                jsonPath("$[0].routeStops[0].location.name").value(locAveiro.getName()),
                jsonPath("$[0].routeStops[0].location.id").value(locAveiro.getId()),
                jsonPath("$[1].routeStops[0].location.name").value(locPorto.getName()),
                jsonPath("$[1].routeStops[0].location.id").value(locPorto.getId()),
                jsonPath("$[2].routeStops[0].location.name").value(locAveiro.getName()),
                jsonPath("$[2].routeStops[0].location.id").value(locAveiro.getId()));

        verify(routeService, times(1)).findAll();
    }

    @Test
    void testGetAllRoutesStartingInAveiro() throws Exception {
        when(routeService.findStartingWithLocation(locAveiro.getName())).thenReturn(Arrays.asList(route1, route3));

        mockMvc.perform(get("/api/routes?startingAt=Aveiro")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasSize(2)),
                jsonPath("$[0].routeStops[0].location.name").value(locAveiro.getName()),
                jsonPath("$[0].routeStops[0].location.id").value(locAveiro.getId()),
                jsonPath("$[1].routeStops[0].location.name").value(locAveiro.getName()),
                jsonPath("$[1].routeStops[0].location.id").value(locAveiro.getId()));

        verify(routeService, times(1)).findStartingWithLocation(locAveiro.getName());
    }

    @Test
    void testGetAllRoutesStartingInvalid() throws Exception {
        when(routeService.findStartingWithLocation(locFaro.getName())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/routes?startingAt=Faro")).andExpectAll(
                status().isNotFound(),
                jsonPath("$").doesNotExist());

        verify(routeService, times(1)).findStartingWithLocation(locFaro.getName());
    }
}
