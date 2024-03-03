package com.tqs108636.lab3_2cars.controller;

import com.tqs108636.lab3_2cars.data.Car;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/cars")
public class CarController {
    // Request parameter "filters", so that we can pass
    @GetMapping()
    public List<Car> getAllCars(@RequestParam("maker") String maker) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> getCars(@PathVariable("id") Long carId) {
        return null;
    }

}
