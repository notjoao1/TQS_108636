package com.tqs108636.busservicebackend.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.dto.ReservationDTO;
import com.tqs108636.busservicebackend.model.Reservation;
import com.tqs108636.busservicebackend.service.IReservationService;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {
    IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Reservation> getReservationDetails(@PathVariable("uuid") UUID uuid) {
        Optional<Reservation> optionalReservation = reservationService.findReservationByUUID(uuid);
        if (optionalReservation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalReservation.get());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Optional<Reservation> optionalReservation = reservationService.createReservation(reservationDTO);
        if (optionalReservation.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(optionalReservation.get());
    }
}
