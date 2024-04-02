package com.tqs108636.busservicebackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.TestUtils;
import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.service.IReservationService;

@WebMvcTest(ReservationController.class)
class ReservationControllerMockServiceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    IReservationService reservationService;

    Trip trip1, trip2;
    Reservation reservation1, reservation2;

    @BeforeEach
    void setup() {
        trip1 = new Trip(null, null, 0, 2); // 2 seats total
        reservation1 = new Reservation(UUID.randomUUID(), trip1, 0, "Pessoa1");
        trip2 = new Trip(null, null, 0, 1); // 1 seat total
        reservation2 = new Reservation(UUID.randomUUID(), trip2, 0, "Pessoa2");

    }

    @Test
    void testCreateReservation_ForValidTrip_BusNotFull() throws IOException, Exception {
        when(reservationService.createReservation(any())).thenReturn(Optional.of(reservation1));
        mockMvc.perform(post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(new ReservationDTO(trip1.getId(), 1, "Pessoa1")))).andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(reservation1.getId().toString()),
                        jsonPath("$.trip.id").value(trip1.getId()),
                        jsonPath("$.clientName").value("Pessoa1"));
    }

    @Test
    void testCreateReservation_ForFullBusTrip() throws IOException, Exception {
        when(reservationService.createReservation(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(new ReservationDTO(trip2.getId(), 1, "Pessoa2")))).andExpectAll(
                        status().isBadRequest());
    }

    @Test
    void testCreateReservation_ForInvalidTrip() throws IOException, Exception {
        when(reservationService.createReservation(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(new ReservationDTO(1000L, 0, "PessoaX")))).andExpectAll(
                        status().isBadRequest());
    }

    @Test
    void testCreateReservation_WithTakenSeatNumber() throws IOException, Exception {
        when(reservationService.createReservation(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(new ReservationDTO(trip1.getId(), 0, "Pessoa1")))).andExpectAll(
                        status().isBadRequest());
    }

    @Test
    void testFindReservation_ForValidUUID() throws Exception {
        when(reservationService.findReservationByUUID(any())).thenReturn(Optional.of(reservation1));

        mockMvc.perform(get(String.format("/api/reservations/%s", reservation1.getId()))).andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(reservation1.getId().toString()));
    }

    @Test
    void testFindReservation_ForInvalidUUID() throws Exception {
        UUID testUuid = UUID.randomUUID();
        when(reservationService.findReservationByUUID(testUuid)).thenReturn(Optional.empty());

        mockMvc.perform(get(String.format("/api/reservations/%s", testUuid))).andExpectAll(
                status().isNotFound(),
                jsonPath("$").doesNotExist());
    }
}
