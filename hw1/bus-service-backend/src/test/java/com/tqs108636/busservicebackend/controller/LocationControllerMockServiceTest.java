package com.tqs108636.busservicebackend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.service.ILocationService;

@WebMvcTest
class LocationControllerMockServiceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ILocationService locationService;

    Location loc1, loc2, loc3, loc4;

    @BeforeEach
    public void setup() {
        loc1 = new Location(1L, "Aveiro", null);
        loc2 = new Location(100L, "Porto", null);
        loc3 = new Location(10000L, "Braga", null);
        loc4 = new Location(100000L, "Faro", null);
    }

    @Test
    void testGetAllLocations() throws Exception {
        when(locationService.findAll()).thenReturn(Arrays.asList(loc1, loc2, loc3));

        mockMvc.perform(get("/api/locations")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(loc1.getId()),
                jsonPath("$[0].name").value("Aveiro"),
                jsonPath("$[1].id").value(loc2.getId()),
                jsonPath("$[1].name").value("Porto"),
                jsonPath("$[2].id").value(loc3.getId()),
                jsonPath("$[2].name").value("Braga"));

        verify(locationService, times(1)).findAll();
    }

    @Test
    void testGetValidLocationById() throws Exception {
        when(locationService.findLocationById(1L)).thenReturn(Optional.of(loc1));

        mockMvc.perform(get("/api/locations/1"))
                .andExpectAll(status().isOk(), jsonPath("$.name").value(loc1.getName()));

        verify(locationService, times(1)).findLocationById(1L);
    }

    @Test
    void testGetInvalidLocation() throws Exception {
        when(locationService.findLocationById(0L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/locations/0")).andExpectAll(status().isNotFound(), jsonPath("$").doesNotExist());

        verify(locationService, times(1)).findLocationById(0L);
    }

    @Test
    void testGetConnectedLocations() throws Exception {
        when(locationService.findConnectedLocations("Aveiro")).thenReturn(Arrays.asList(loc2, loc3));

        mockMvc.perform(get("/api/locations?connectedTo=Aveiro")).andExpectAll(status().isOk(),
                jsonPath("$[0].id").value(loc2.getId()), jsonPath("$[1].id").value(loc3.getId()),
                jsonPath("$[0].name").value(loc2.getName()), jsonPath("$[1].name").value(loc3.getName()));

        verify(locationService, times(1)).findConnectedLocations(loc1.getName());
    }

    @Test
    void testGetInvalidConnectedLocations() throws Exception {
        when(locationService.findConnectedLocations("Vila Real")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/locations?connectedTo=Vila Real")).andExpectAll(status().isNotFound(),
                jsonPath("$").doesNotExist());

        verify(locationService, times(1)).findConnectedLocations("Vila Real");
    }

}
