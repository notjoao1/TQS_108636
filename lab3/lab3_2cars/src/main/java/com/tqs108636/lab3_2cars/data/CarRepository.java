package com.tqs108636.lab3_2cars.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    public Car findByCarId(Long carId);

    public List<Car> findByMaker(String maker);

    public List<Car> findAll();
}
