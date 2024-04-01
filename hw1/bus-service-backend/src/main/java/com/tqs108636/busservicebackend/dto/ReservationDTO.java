package com.tqs108636.busservicebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @NotNull
    private Long tripId;

    @NotNull
    private int seatNumber;

    @NotNull
    private String clientName;
}
