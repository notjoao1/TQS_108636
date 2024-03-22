package com.tqs108636.lab3_2cars.dto;

public class CarDTO {
    private Long carId;
    private String maker;
    private String model;

    public CarDTO() {
    }

    public CarDTO(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public CarDTO(Long carId, String maker, String model) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return String.format("%d - %s %s", carId, maker, model);
    }

}
