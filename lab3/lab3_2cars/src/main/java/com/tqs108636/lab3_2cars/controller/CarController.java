package com.tqs108636.lab3_2cars.controller;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.service.CarManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    // Request parameter "maker", to filter by car maker
    @Autowired
    private CarManagerService carService;

    CarController(CarManagerService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(name = "maker", required = false) String maker) {
        if (maker != null) {
            List<Car> carsByMaker = carService.getCarsByMaker(maker);
            if (carsByMaker.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(carsByMaker);
        }
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping()
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        if (car == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<Car>(carService.save(car), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCars(@PathVariable("id") Long carId) {
        Optional<Car> carOptional = carService.getCarDetails(carId);
        if (carOptional.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carOptional.get());
    }

}
