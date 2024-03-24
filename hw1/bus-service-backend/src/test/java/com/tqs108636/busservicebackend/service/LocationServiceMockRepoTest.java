package com.tqs108636.busservicebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.tqs108636.busservicebackend.service.impl.LocationService;

@ExtendWith(MockitoExtension.class)
class LocationServiceMockRepoTest {
    @Mock
    LocationRepository locationRepository;

    @Mock
    RouteRepository routeRepository;

    @InjectMocks
    LocationService locationService;

    Location locAveiro, locPorto, locBraga, locFaro;
    RouteStop rs1, rs2, rs3, rs4, rs5;
    Route route1, route2;

    @BeforeEach
    void setup() {
        // route 1: Aveiro -> Porto -> Braga
        // route 2: Braga -> Porto
        route1 = new Route(1L, 70, null);
        route2 = new Route(2L, 30, null);

        locAveiro = new Location(1L, "Aveiro", null);
        locPorto = new Location(100L, "Porto", null);
        locBraga = new Location(10000L, "Braga", null);
        locFaro = new Location(100000L, "Faro", null);

        rs1 = new RouteStop(1L, locAveiro, route1, 0, 0);
        rs2 = new RouteStop(2L, locPorto, route1, 1, 40);
        rs3 = new RouteStop(3L, locBraga, route1, 2, 30);
        route1.setRouteStops(Arrays.asList(rs1, rs2, rs3));

        rs4 = new RouteStop(4L, locBraga, route2, 0, 0);
        rs5 = new RouteStop(5L, locPorto, route1, 1, 30);
        route2.setRouteStops(Arrays.asList(rs4, rs5));

    }

    @Test
    void testFindAll() {
        when(locationRepository.findAll()).thenReturn(Arrays.asList(locAveiro, locPorto, locBraga, locFaro));

        List<Location> allLocations = locationService.findAll();

        assertEquals(4, allLocations.size());

        assertTrue(allLocations.contains(locAveiro));
        assertTrue(allLocations.contains(locPorto));
        assertTrue(allLocations.contains(locBraga));
        assertTrue(allLocations.contains(locFaro));

        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testFindValidLocationByName() {
        when(locationRepository.findByName(locPorto.getName())).thenReturn(Optional.of(locPorto));

        Optional<Location> foundLocation = locationService.findByName(locPorto.getName());

        assertTrue(foundLocation.isPresent());
        assertEquals(locPorto, foundLocation.get());

        verify(locationRepository, times(1)).findByName(locPorto.getName());
    }

    @Test
    void testFindInvalidLocationByName() {
        when(locationRepository.findByName("testbroken")).thenReturn(Optional.empty());

        Optional<Location> foundLocation = locationService.findByName("testbroken");

        assertTrue(foundLocation.isEmpty());

        verify(locationRepository, times(1)).findByName("testbroken");
    }

    @Test
    void testFindConnectedLocationsToPorto() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(locPorto));
        when(routeRepository.findByStopLocation(locPorto)).thenReturn(Arrays.asList(route1, route2));

        List<Location> connectedLocations = locationService.findConnectedLocations(locPorto.getName());

        // Aveiro, Braga
        assertEquals(2, connectedLocations.size());
        assertTrue(connectedLocations.contains(locAveiro));
        assertTrue(connectedLocations.contains(locBraga));

        verify(routeRepository, times(1)).findByStopLocation(locPorto);
        verify(locationRepository, times(1)).findById(locPorto.getId());
    }

    @Test
    void testFindConnectedLocationsToFaro() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(locFaro));
        when(routeRepository.findByStopLocation(locFaro)).thenReturn(new ArrayList<>());

        List<Location> connectedLocations = locationService.findConnectedLocations(locFaro.getName());

        assertTrue(connectedLocations.isEmpty());

        verify(routeRepository, times(1)).findByStopLocation(locFaro);
        verify(locationRepository, times(1)).findById(locFaro.getId());

    }

    @Test
    void testFindConnectedLocationsToInvalid() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        List<Location> connectedLocations = locationService.findConnectedLocations("lala");

        assertTrue(connectedLocations.isEmpty());

        verify(locationRepository, times(1)).findById(anyLong());
        verify(routeRepository, times(0)).findByStopLocation(any());
    }
}
