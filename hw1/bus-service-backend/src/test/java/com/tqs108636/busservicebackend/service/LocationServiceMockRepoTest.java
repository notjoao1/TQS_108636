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
import com.tqs108636.busservicebackend.repository.RouteStopRepository;

@ExtendWith(MockitoExtension.class)
class LocationServiceMockRepoTest {
    @Mock
    LocationRepository locationRepository;

    @Mock
    RouteStopRepository routeStopRepository;

    @InjectMocks
    ILocationService locationService;

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
    void testFindByValidId() {
        when(locationRepository.findById(10000L)).thenReturn(Optional.of(locBraga));

        Optional<Location> foundLocation = locationRepository.findById(10000L);

        assertTrue(foundLocation.isPresent());
        assertEquals(locBraga, foundLocation.get());

        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByInvalidId() {
        when(locationRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Location> foundLocation = locationRepository.findById(2L);

        assertTrue(foundLocation.isEmpty());
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
        when(routeStopRepository.findByLocation(locPorto)).thenReturn(Arrays.asList(rs1, rs2, rs3));

        List<Location> connectedLocations = locationService.findConnectedLocations(locPorto.getId());

        // Aveiro, Braga
        assertEquals(2, connectedLocations.size());
        assertTrue(connectedLocations.contains(locAveiro));
        assertTrue(connectedLocations.contains(locBraga));

        verify(routeStopRepository, times(1)).findByLocation(locPorto);
    }

    @Test
    void testFindConnectedLocationsToFaro() {
        when(routeStopRepository.findByLocation(locFaro)).thenReturn(new ArrayList<>());

        List<Location> connectedLocations = locationService.findConnectedLocations(locFaro.getId());

        assertTrue(connectedLocations.isEmpty());

        verify(routeStopRepository, times(1)).findByLocation(locFaro);
    }
}
