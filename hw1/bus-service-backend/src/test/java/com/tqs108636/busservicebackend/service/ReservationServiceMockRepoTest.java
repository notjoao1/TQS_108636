package com.tqs108636.busservicebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.model.Trip;
import com.tqs108636.busservicebackend.repository.ReservationRepository;
import com.tqs108636.busservicebackend.repository.TripRepository;
import com.tqs108636.busservicebackend.service.impl.ReservationService;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockRepoTest {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    TripRepository tripRepository;

    @InjectMocks
    ReservationService reservationService;

    Trip trip1, trip2;

    Reservation reservation1, reservation2;

    @BeforeEach
    void setup() {
        // trip1 - bus is not full
        trip1 = new Trip(null, null, 0, 2); // 2 seats
        reservation1 = new Reservation(UUID.randomUUID(), trip1, 0, "Pessoa1");

        // trip2 - bus is full
        trip2 = new Trip(null, null, 0, 1); // 1 seat
        reservation2 = new Reservation(UUID.randomUUID(), trip2, 0, "Pessoa2");
    }

    @Test
    void testCreateReservation_ValidTrip() {
        when(tripRepository.findById(trip1.getId())).thenReturn(Optional.of(trip1));
        when(reservationRepository.countByTrip(trip1)).thenReturn(1);
        when(reservationRepository.save(any())).thenReturn(reservation1);

        Optional<Reservation> optionalReservation = reservationService
                .createReservation(new ReservationDTO(trip1.getId(), 0, "Pessoa1"));

        assertTrue(optionalReservation.isPresent());
        assertEquals("Pessoa1", optionalReservation.get().getClientName());
        assertEquals(optionalReservation.get().getTrip().getId(), trip1.getId());
    }

    @Test
    void testCreateReservation_ForFullTrip() {
        when(tripRepository.findById(trip2.getId())).thenReturn(Optional.of(trip2));
        when(reservationRepository.countByTrip(trip2)).thenReturn(1);

        Optional<Reservation> optionalReservation = reservationService
                .createReservation(new ReservationDTO(trip2.getId(), 0, "Pessoa2"));

        assertTrue(optionalReservation.isEmpty());

        verify(reservationRepository, times(0)).save(any());
    }

    @Test
    void testCreateReservation_ForNonExistingTrip() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Reservation> optionalReservation = reservationService
                .createReservation(new ReservationDTO(10000L, 0, "PessoaX"));

        assertTrue(optionalReservation.isEmpty());

        verify(reservationRepository, times(0)).countByTrip(any());
    }

    @Test
    void testCreateReservation_ForInvalidSeatNumber() {
        when(tripRepository.findById(trip1.getId())).thenReturn(Optional.of(trip1));
        when(reservationRepository.countByTrip(trip1)).thenReturn(1);

        Optional<Reservation> optionalReservation = reservationService
                .createReservation(new ReservationDTO(trip1.getId(), 2, "Pessoa1"));

        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void test_IsBusTripFull_ForFullTrip() {
        when(reservationRepository.countByTrip(trip2)).thenReturn(1);

        assertTrue(reservationService.isBusTripFull(trip2));
    }

    @Test
    void test_IsBusTripFull_ForNotFullTrip() {
        when(reservationRepository.countByTrip(trip1)).thenReturn(1);

        assertFalse(reservationService.isBusTripFull(trip1));
    }

    @Test
    void test_IsSeatNumberValid() {
        // trip 1 has 2 seats. valid seat numbers -> 0, 1
        assertTrue(reservationService.isSeatNumberValid(trip1, 0));
        assertTrue(reservationService.isSeatNumberValid(trip1, 1));
        assertFalse(reservationService.isSeatNumberValid(trip1, 2));
    }

    @Test
    void test_GetReservationDetails_ForValidToken() {
        when(reservationRepository.findById(reservation1.getId())).thenReturn(Optional.of(reservation1));

        Optional<Reservation> optionalReservation = reservationService.findReservationByUUID(reservation1.getId());

        assertTrue(optionalReservation.isPresent());
        assertEquals(reservation1.getId(), optionalReservation.get().getId());

        verify(reservationRepository, times(1)).findById(reservation1.getId());
    }

    @Test
    void test_GetReservationDetails_InvalidToken() {
        UUID randomUUID = UUID.randomUUID();
        when(reservationRepository.findById(randomUUID)).thenReturn(Optional.empty());

        Optional<Reservation> optionalReservation = reservationService.findReservationByUUID(randomUUID);

        assertTrue(optionalReservation.isEmpty());

        verify(reservationRepository, times(1)).findById(randomUUID);
    }
}
