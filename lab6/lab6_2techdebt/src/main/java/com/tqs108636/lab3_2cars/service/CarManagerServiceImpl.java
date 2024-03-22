package com.tqs108636.lab3_2cars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.data.CarRepository;
import com.tqs108636.lab3_2cars.dto.CarDTO;

@Service
public class CarManagerServiceImpl implements CarManagerService {

    private CarRepository carRepository;

    @Autowired
    CarManagerServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(CarDTO carDTO) {
        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setMaker(carDTO.getMaker());
        car.setCarId(carDTO.getCarId());
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarDetails(Long carId) {
        if (carId == null)
            return Optional.empty();
        return carRepository.findByCarId(carId);
    }

    @Override
    public List<Car> getCarsByMaker(String maker) {
        return carRepository.findByMaker(maker);
    }

}
