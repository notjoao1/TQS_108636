package com.tqs108636.lab3_2cars;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.data.CarRepository;
import com.tqs108636.lab3_2cars.service.CarManagerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CarService_MockRepositoryTest {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerServiceImpl carManagerService;

    private Car car1, car2, car3;

    @BeforeEach
    void setup() {
        car1 = new Car("Renault", "Megane");
        car1.setCarId(1000L);
        car2 = new Car("Nissan", "350Z");
        car2.setCarId(5000L);
        car3 = new Car("Renault", "Clio");
        car3.setCarId(10000L);

        List<Car> allCars = new ArrayList<>(Arrays.asList(car1, car2, car3));

        List<Car> allRenaults = new ArrayList<>(Arrays.asList(car1, car3));

        when(carRepository.findByCarId(1000L)).thenReturn(Optional.of(car1));
        when(carRepository.findByCarId(5000L)).thenReturn(Optional.of(car2));
        when(carRepository.findByCarId(10000L)).thenReturn(Optional.of(car3));
        when(carRepository.findByMaker("Renault")).thenReturn(allRenaults);
        when(carRepository.findAll()).thenReturn(allCars);
    }

    @Test
    public void whenSaveCar1_ThenReturnCar1() {
        when(carRepository.save(car1)).thenReturn(car1);

        assertEquals(carManagerService.save(car1), car1);
    }

    @Test
    public void whenGetInvalidCar_thenReturnEmptyOptional() {
        when(carRepository.findByCarId(1L)).thenReturn(Optional.empty()); // doesn't exist

        assertTrue(carManagerService.getCarDetails(1L).isEmpty());
        verify(carRepository, times(1)).findByCarId(1L);
    }

    @Test
    public void whenGetAllCars_thenReturnAllCars() {
        List<Car> getAllCars = carManagerService.getAllCars();

        assertThat(getAllCars).contains(car1, car2, car3).hasSize(3);

        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void givenCarMakerWith2Cars_thenReturn2Cars() {
        List<Car> returnedCars = carManagerService.getCarsByMaker("Renault");

        assertThat(returnedCars).contains(car1, car3).hasSize(2);
        assertThat(returnedCars).extracting(Car::getMaker).allMatch(m -> m.equals("Renault"));

        verify(carRepository, times(1)).findByMaker("Renault");
    }

    @Test
    public void givenInvalidCarMaker_thenReturnEmptyList() {
        when(carRepository.findByMaker("123")).thenReturn(new ArrayList<>());
        List<Car> returnedCars = carManagerService.getCarsByMaker("123");

        assertThat(returnedCars).isNotNull().hasSize(0);
        verify(carRepository, times(1)).findByMaker("123");
    }
}
