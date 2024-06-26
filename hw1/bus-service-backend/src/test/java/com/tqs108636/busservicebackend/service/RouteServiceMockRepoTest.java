package com.tqs108636.busservicebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;
import com.tqs108636.busservicebackend.repository.LocationRepository;
import com.tqs108636.busservicebackend.repository.RouteRepository;
import com.tqs108636.busservicebackend.service.impl.RouteService;

@ExtendWith(MockitoExtension.class)
class RouteServiceMockRepoTest {
    @Mock
    RouteRepository routeRepository;

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    RouteService routeService;

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
    }

    @Test
    void testFindRoutes_FromAveiro_ToPorto() {
        when(locationRepository.findByName(locAveiro.getName())).thenReturn(Optional.of(locAveiro));
        when(locationRepository.findByName(locPorto.getName())).thenReturn(Optional.of(locPorto));
        when(routeRepository.findRoutesFromLocationToLocation(locAveiro, locPorto))
                .thenReturn(Arrays.asList(route1));

        List<Route> routesFromAveiroToPorto = routeService.findRouteFromLocationToLocation(locAveiro.getName(),
                locPorto.getName());

        assertEquals(1, routesFromAveiroToPorto.size());
        assertTrue(routesFromAveiroToPorto.contains(route1));

        verify(routeRepository, times(1)).findRoutesFromLocationToLocation(locAveiro, locPorto);
        verify(locationRepository, times(1)).findByName(locAveiro.getName());
        verify(locationRepository, times(1)).findByName(locPorto.getName());
    }

    @Test
    void testFindRoutes_FromFaro_ToAveiro() {
        when(locationRepository.findByName(locFaro.getName())).thenReturn(Optional.of(locFaro));
        when(locationRepository.findByName(locAveiro.getName())).thenReturn(Optional.of(locAveiro));
        when(routeRepository.findRoutesFromLocationToLocation(locFaro, locAveiro)).thenReturn(new ArrayList<>());

        List<Route> routesFromFaroToAveiro = routeService.findRouteFromLocationToLocation(locFaro.getName(),
                locAveiro.getName());

        assertTrue(routesFromFaroToAveiro.isEmpty());

        verify(routeRepository, times(1)).findRoutesFromLocationToLocation(locFaro, locAveiro);
        verify(locationRepository, times(1)).findByName(locFaro.getName());
        verify(locationRepository, times(1)).findByName(locAveiro.getName());
    }

    @Test
    void testFindAllRoutes() {
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        List<Route> allRoutes = routeService.findAll();

        assertEquals(3, allRoutes.size());
        assertEquals(route1, allRoutes.get(0));
        assertEquals(route2, allRoutes.get(1));
        assertEquals(route3, allRoutes.get(2));

        verify(routeRepository, times(1)).findAll();
    }

    @Test
    void testFindValidRouteById() {
        when(routeRepository.findById(3L)).thenReturn(Optional.of(route3));

        Optional<Route> foundRoute = routeService.findById(3L);

        assertTrue(foundRoute.isPresent());
        assertEquals(route3, foundRoute.get());

        verify(routeRepository, times(1)).findById(3L);
    }

    @Test
    void testFindInvalidRouteById() {
        when(routeRepository.findById(1000L)).thenReturn(Optional.empty());

        Optional<Route> foundRoute = routeService.findById(1000L);

        assertTrue(foundRoute.isEmpty());

        verify(routeRepository, times(1)).findById(1000L);
    }
}
