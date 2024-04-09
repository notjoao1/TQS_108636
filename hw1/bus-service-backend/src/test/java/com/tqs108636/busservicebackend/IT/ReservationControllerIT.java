package com.tqs108636.busservicebackend.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_it.properties")
class ReservationControllerIT {
    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testGetReservationDetails_ValidUUID() {
        // INSERT INTO Reservation (id, trip_id, seatNumber, clientName)
        // VALUES ('3ba26311-323d-4d2d-b12d-d77dde16ca17', 3, 7, 'Client A');
        UUID uuid = UUID.fromString("3ba26311-323d-4d2d-b12d-d77dde16ca17");

        ResponseEntity<Reservation> response = restTemplate.exchange(
                String.format("/api/reservations/%s", uuid),
                HttpMethod.GET,
                null,
                Reservation.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Reservation reservationResponse = response.getBody();
        assertEquals("Client A", reservationResponse.getClientName());
        assertEquals(3, reservationResponse.getTrip().getId());
        assertEquals(7, reservationResponse.getSeatNumber());
    }

    @Test
    void testGetReservationDetails_InvalidUUID() {
        UUID randomUuid = UUID.randomUUID();

        ResponseEntity<Reservation> response = restTemplate.exchange(
                String.format("/api/reservations/%s", randomUuid),
                HttpMethod.GET,
                null,
                Reservation.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Reservation reservationResponse = response.getBody();
        assertNull(reservationResponse);
    }

    @Test
    void test_CreateReservation_ForValidTrip_ValidSeatNumber() {
        ReservationDTO reservationDTO = new ReservationDTO(1L, 0, "Pessoa_Fixe");

        ResponseEntity<Reservation> response = restTemplate.exchange(
                String.format("/api/reservations"),
                HttpMethod.POST,
                new HttpEntity<>(reservationDTO),
                Reservation.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Reservation reservationResponse = response.getBody();
        assertNotNull(reservationResponse);
        assertEquals("Pessoa_Fixe", reservationResponse.getClientName());
        assertEquals(1L, reservationResponse.getTrip().getId());
        assertEquals(0, reservationResponse.getSeatNumber());
    }

    @Test
    void test_CreateReservation_ForInvalidTrip_ValidSeatNumber() {
        ReservationDTO reservationDTO = new ReservationDTO(10000L, 0, "Pessoa_Fixe");

        ResponseEntity<Reservation> response = restTemplate.exchange(
                String.format("/api/reservations"),
                HttpMethod.POST,
                new HttpEntity<>(reservationDTO),
                Reservation.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Or the expected status code
        Reservation reservationResponse = response.getBody();
        assertNull(reservationResponse);
    }

    @Test
    void test_CreateReservation_ForValidTrip_InvalidSeatNumber() {
        // There's a reservation for trip 1 + seat 1 - cannot be reserved again
        ReservationDTO reservationDTO = new ReservationDTO(1L, 1, "Pessoa_Fixe");

        ResponseEntity<Reservation> response = restTemplate.exchange(
                String.format("/api/reservations"),
                HttpMethod.POST,
                new HttpEntity<>(reservationDTO),
                Reservation.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Or the expected status code
        Reservation reservationResponse = response.getBody();
        assertNull(reservationResponse);
    }
}
