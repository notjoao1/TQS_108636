package com.tqs108636.busservicebackend.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.tqs108636.busservicebackend.model.Location;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_it.properties")
class LocatioonControllerIT {
    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testGetAllLocations() {
        ResponseEntity<List<Location>> response = restTemplate.exchange(
                "/api/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Location> locationResponse = response.getBody();
        assertEquals(4, locationResponse.size());
    }

    @Test
    void testGetAllLocations_ConnectedToAveiro() {
        ResponseEntity<List<Location>> response = restTemplate.exchange(
                "/api/locations?connectedTo=Aveiro",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Location> connectedLocations = response.getBody();
        assertEquals(2, connectedLocations.size());
        assertTrue(connectedLocations.stream().anyMatch(l -> l.getName().equals("Porto")));
        assertTrue(connectedLocations.stream().anyMatch(l -> l.getName().equals("Braga")));
    }

    @Test
    void testGetAllLocations_ConnectedToFaro() {
        ResponseEntity<List<Location>> response = restTemplate.exchange(
                "/api/locations?connectedTo=Faro",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        List<Location> connectedLocations = response.getBody();
        assertNull(connectedLocations); // empty body
    }
}
