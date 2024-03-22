package com.tqs108636.lab3_2cars.service;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.dto.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarManagerService {
    Car save(CarDTO c);

    List<Car> getAllCars();

    Optional<Car> getCarDetails(Long carId);

    List<Car> getCarsByMaker(String maker);
}
