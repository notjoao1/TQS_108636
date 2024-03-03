package com.tqs108636.lab3_2cars.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Car {
    @Id
    private Long carId;

    private String maker;

    private String model;

    public Car() {
    }

    public Car(Long carId, String maker, String model) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return carId + " - " + maker + " " + model;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        Car otherCar = (Car) other;
        return Objects.equals(this.carId, otherCar.getCarId());
    }
}
