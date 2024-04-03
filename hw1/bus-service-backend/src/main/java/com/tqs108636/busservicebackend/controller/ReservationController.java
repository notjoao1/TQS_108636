package com.tqs108636.busservicebackend.controller;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private IReservationService reservationService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Reservation> getReservationDetails(@PathVariable("uuid") UUID uuid) {
        logger.info("GET /api/reservations/{}'", uuid);
        Optional<Reservation> optionalReservation = reservationService.findReservationByUUID(uuid);
        if (optionalReservation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalReservation.get());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
        logger.info("POST /api/reservations. Body: {}'", reservationDTO);

        Optional<Reservation> optionalReservation = reservationService.createReservation(reservationDTO);
        if (optionalReservation.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.status(HttpStatus.CREATED).body(optionalReservation.get());
    }
}
