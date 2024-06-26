package com.tqs108636.busservicebackend.dto;

import java.time.LocalDateTime;

import com.tqs108636.busservicebackend.model.Route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private Route route;
    private LocalDateTime departureTime;
    private float price;
    private int numberOfSeats;
}
