package com.tqs108636.lab3_2cars.service;

import com.tqs108636.lab3_2cars.data.Car;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface CarManagerService {
    Car save(Car c);

    List<Car> getAllCars();

    Optional<Car> getCarDetails(Long carId);

    List<Car> getCarsByMaker(String maker);
}
