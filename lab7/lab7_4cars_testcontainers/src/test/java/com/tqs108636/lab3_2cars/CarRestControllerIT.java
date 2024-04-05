package com.tqs108636.lab3_2cars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.data.CarRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties")
public class CarRestControllerIT {
    @LocalServerPort
    int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    void cleanUp() {
        carRepository.deleteAll();
    }

    @Test
    public void whenValidCar_thenCreateCar() {
        Car car = new Car("Renault", "Megane");
        ResponseEntity<Car> postCar = restTemplate.postForEntity("/api/cars", car, Car.class);

        Optional<Car> returnedCar = carRepository.findById(postCar.getBody().getCarId());

        assertThat(postCar.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnedCar).isNotEmpty();
        assertThat(returnedCar.get()).isEqualTo(postCar.getBody());
    }

    @Test
    public void whenInvalidCar_thenDontCreateCar() {
        Car invalidCar = null;
        restTemplate.postForEntity("/api/cars", invalidCar, Car.class);

        assertThat(carRepository.findAll()).isEmpty();
    }

    @Test
    public void givenCreate2Renaults_whenFindByMakerRenault_thenReturn2Renaults() {
        Car car1 = new Car("Renault", "Megane");
        Car car2 = new Car("Renault", "Clio");
        Car car3 = new Car("Nissan", "Skyline GT-R R34");
        car1 = restTemplate.postForEntity("/api/cars", car1, Car.class).getBody();
        car2 = restTemplate.postForEntity("/api/cars", car2, Car.class).getBody();
        car3 = restTemplate.postForEntity("/api/cars", car3, Car.class).getBody();

        // assert that 3 cars are in database
        assertThat(carRepository.findAll()).hasSize(3).containsOnly(car1, car2, car3);
        // assert that only 2 of them are renaults
        assertThat(carRepository.findByMaker("Renault")).hasSize(2).contains(car1, car2);
    }

    @Test
    public void givenCreate2Nissans_whenGetAllNissans_thenReturn2() {
        Car car1 = new Car("Toyota", "Supra");
        Car car2 = new Car("Nissan", "Silvia");
        // create 3rd car simply to make sure we don't return anything extra when we
        // call GET for only nissan maker
        Car car3 = new Car("Nissan", "Skyline GT-R R34");

        // create all 3 cars
        car1 = restTemplate.postForEntity("/api/cars", car1, Car.class).getBody();
        car2 = restTemplate.postForEntity("/api/cars", car2, Car.class).getBody();
        car3 = restTemplate.postForEntity("/api/cars", car3, Car.class).getBody();

        // get all nissan cars
        ResponseEntity<List<Car>> nissans = restTemplate.exchange("/api/cars?maker=Nissan", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(nissans.getBody()).hasSize(2).containsOnly(car2, car3);
    }

}