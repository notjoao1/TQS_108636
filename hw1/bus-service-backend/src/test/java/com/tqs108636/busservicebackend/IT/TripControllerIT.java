package com.tqs108636.busservicebackend.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

import com.tqs108636.busservicebackend.dto.TripDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_it.properties")
class TripControllerIT {
    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testGetAllTrips() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TripDTO> tripResponse = response.getBody();
        assertEquals(8, tripResponse.size());
    }

    @Test
    void testGetAllTrips_FromAveiro_ToBraga() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Aveiro&to=Braga",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TripDTO> tripResponse = response.getBody();
        assertEquals(7, tripResponse.size());
    }

    @Test
    void testGetAllTrips_FromAveiro_ToNull() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Aveiro",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        List<TripDTO> tripResponse = response.getBody();
        assertNull(tripResponse);
    }

    @Test
    void testGetAllTrips_FromNull_ToBraga() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?to=Braga",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        List<TripDTO> tripResponse = response.getBody();
        assertNull(tripResponse); // empty response
    }

    @Test
    void testGetAllUpcomingTrips_FromAveiro_ToBraga() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Aveiro&to=Braga&upcoming=true",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

        List<TripDTO> tripResponse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, tripResponse.size());
        assertTrue(tripResponse.stream().allMatch(t -> t.getDepartureTime()
                .compareTo(LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS, 0, ZoneOffset.UTC)) > 0));
    }

    @Test
    void testGetAllUpcomingTrips_FromBraga_ToFaro() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Braga&to=Faro&upcoming=true",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        List<TripDTO> tripResponse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(tripResponse.isEmpty());
    }

    @Test
    void testGetAllTrips_CurrencyUSD() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?currency=USD",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        List<TripDTO> tripResponse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(tripResponse.isEmpty());
        assertEquals(8, tripResponse.size());
    }

    @Test
    void testGetAllTrips_FromAveiro_ToBraga_CurrencyKRW() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Aveiro&to=Braga&currency=KRW",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TripDTO> tripResponse = response.getBody();
        assertEquals(7, tripResponse.size());
        // this works if rate from KRW/EUR > 17 (its 1463 now)
        // lowest EUR price is 6€ and highest is 20€
        // we are checking if all prices are greater than 100KRW
        assertTrue(tripResponse.stream().allMatch(t -> t.getPrice() > 100.0f));
    }

    @Test
    void testGetAllUpcomingTrips_FromAveiro_ToBraga_CurrencyKRW() {
        ResponseEntity<List<TripDTO>> response = restTemplate.exchange(
                "/api/trips?from=Aveiro&to=Braga&upcoming=true&currency=KRW",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TripDTO>>() {
                });

        final long CURRENT_TIME_SECONDS = Instant.now().getEpochSecond();

        List<TripDTO> tripResponse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, tripResponse.size());
        assertTrue(tripResponse.stream().allMatch(t -> t.getDepartureTime()
                .compareTo(LocalDateTime.ofEpochSecond(CURRENT_TIME_SECONDS, 0, ZoneOffset.UTC)) > 0));
        assertTrue(tripResponse.stream().allMatch(t -> t.getPrice() > 100.0f));
    }
}
