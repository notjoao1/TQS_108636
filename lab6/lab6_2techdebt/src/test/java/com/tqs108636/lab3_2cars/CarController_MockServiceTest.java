package com.tqs108636.lab3_2cars;

import com.tqs108636.lab3_2cars.controller.CarController;
import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.dto.CarDTO;
import com.tqs108636.lab3_2cars.service.CarManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// This class will not initialize the full application context. We only need to test the controller - use mock service
@WebMvcTest(CarController.class)
public class CarController_MockServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carService; // mock service

    private CarDTO carDTO1, carDTO2, carDTO3;

    private Car car1, car2, car3;

    @BeforeEach
    public void setup() {
        carDTO1 = new CarDTO("Renault", "Megane");
        carDTO1.setCarId(1L);
        carDTO2 = new CarDTO("Nissan", "350Z");
        carDTO2.setCarId(1000L);
        carDTO3 = new CarDTO("Renault", "Clio");
        carDTO3.setCarId(10000L);

        car1 = new Car("Renault", "Megane");
        car1.setCarId(1L);
        car2 = new Car("Nissan", "350Z");
        car2.setCarId(1000L);
        car3 = new Car("Renault", "Clio");
        car3.setCarId(10000L);
    }

    @Test
    public void testGetAllCars() throws Exception {
        ArrayList<Car> cars = new ArrayList<>(Arrays.asList(car1, car2));
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].carId").value(car1.getCarId()),
                        jsonPath("$[1].carId").value(car2.getCarId()));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    public void testGetCarById() throws Exception {
        when(carService.getCarDetails(car1.getCarId())).thenReturn(Optional.of(car1));

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(jsonPath("$.carId", is(1)));
        verify(carService, times(1)).getCarDetails(1L);

    }

    @Test
    public void testPostCar() throws Exception {
        // mock behaviour -> always return car1 on save
        when(carService.save(any())).thenReturn(car1);

        mockMvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(carDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Renault")))
                .andExpect(jsonPath("$.model", is("Megane")));

        // carService.save(car1) should be called a single time
        verify(carService, times(1)).save(any());
    }

    @Test
    public void testGetAllNissans() throws Exception {
        List<Car> nissans = new ArrayList<>(Arrays.asList(car1, car3));
        when(carService.getCarsByMaker("Nissan")).thenReturn(nissans);

        mockMvc.perform(get("/api/cars?maker=Nissan")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
        verify(carService, times(1)).getCarsByMaker("Nissan");
    }

    @Test
    public void testGetInvalidMaker_thenReturnNotFound() throws Exception {
        when(carService.getCarsByMaker("empty")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/cars?maker=empty"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        verify(carService, times(1)).getCarsByMaker("empty");
    }

    @Test
    public void testGetInvalidIDCar() throws Exception {
        when(carService.getCarDetails(-1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cars/-1")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
        verify(carService, times(1)).getCarDetails(-1L);
    }

    @Test
    public void testPostNullCar() throws Exception {
        when(carService.save(null)).thenReturn(null);

        mockMvc.perform(post("/api/cars")).andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist());
    }

}
