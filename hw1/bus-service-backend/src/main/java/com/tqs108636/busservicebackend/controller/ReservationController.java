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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {
    private IReservationService reservationService;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Retrieves a reservation by UUID", description = "Fetches the details of a specific reservation based on its unique identifier (UUID).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))),
            @ApiResponse(responseCode = "404", description = "Reservation by that UUID not found")
    })
    @GetMapping("{uuid}")
    public ResponseEntity<Reservation> getReservationDetails(@PathVariable("uuid") UUID uuid) {
        logger.info("GET /api/reservations/{}'", uuid);
        Optional<Reservation> optionalReservation = reservationService.findReservationByUUID(uuid);
        if (optionalReservation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalReservation.get());
    }

    @PostMapping
    @Operation(summary = "Creates a new reservation", description = "Processes a reservation request and generates a new reservation record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservation request data")
    })
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
        logger.info("POST /api/reservations. Body: {}'", reservationDTO);

        Optional<Reservation> optionalReservation = reservationService.createReservation(reservationDTO);
        if (optionalReservation.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.status(HttpStatus.CREATED).body(optionalReservation.get());
    }
}
