package com.tqs108636.lab3_2cars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.data.CarRepository;

// These tests are really simple, and maybe useless. But when you are using custom queries (@Query),
// you can use unit tests to make sure your query works properly
@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindByRenaultMaker_thenReturnRenaultCars() {
        Car car1 = new Car("Renault", "Megane");
        Car car2 = new Car("Renault", "Clio");
        entityManager.persistAndFlush(car1);
        entityManager.persistAndFlush(car2);

        assertThat(carRepository.findByMaker("Renault")).hasSize(2).contains(car1, car2).extracting(Car::getModel)
                .containsOnly("Megane", "Clio");

    }

    @Test
    void whenFindById1_thenReturnId1() {
        Car car1 = new Car(1L, "Nissan", "350Z");
        entityManager.persistAndFlush(car1);

        assertThat(carRepository.findById(1L)).isEqualTo(car1);
    }

    @Test
    void whenFindInvalidId_thenReturnEmptyOptional() {
        Optional<Car> fromDb = carRepository.findById(-1L);
        assertThat(fromDb).isEmpty();
    }

    @Test
    void given3Cars_whenFindAll_thenReturnAll3Cars() {
        Car car1, car2, car3;
        car1 = new Car("1", "1");
        car2 = new Car("2", "2");
        car3 = new Car("3", "3");
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).containsOnly(car1, car2, car3);
    }

}
