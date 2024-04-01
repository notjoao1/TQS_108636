package com.tqs108636.busservicebackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    @NonNull
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private LocalDateTime departureTime;

    private float priceEuro;

    // If there are 4 seats, valid seat numbers are -> 0, 1, 2, 3
    private int numberOfSeats;

    public Trip(Route route, LocalDateTime departureTime, float priceEuro, int numberOfSeats) {
        this.route = route;
        this.departureTime = departureTime;
        this.priceEuro = priceEuro;
        this.numberOfSeats = numberOfSeats;
    }
}
