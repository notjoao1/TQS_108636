package com.tqs108636.busservicebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @NonNull
    private Location location;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    @NonNull
    private Route route;

    private int stopNumber; // number of the stop (1st, 2nd, 3rd...)

    private float distanceKmFromLastStop;

    public RouteStop(Location location, Route route, int stopNumber, float distanceKmFromLastStop) {
        this.location = location;
        this.route = route;
        this.stopNumber = stopNumber;
        this.distanceKmFromLastStop = distanceKmFromLastStop;
    }
}
