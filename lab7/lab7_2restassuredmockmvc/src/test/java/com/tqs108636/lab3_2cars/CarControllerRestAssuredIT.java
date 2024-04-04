package com.tqs108636.lab3_2cars;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.lab3_2cars.controller.CarController;
import com.tqs108636.lab3_2cars.data.Car;
import com.tqs108636.lab3_2cars.service.CarManagerService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(CarController.class)
class CarControllerRestAssuredIT {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarManagerService carService;

    Car car1, car2, car3;

    @BeforeEach
    public void setup() {
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

        RestAssuredMockMvc.given().mockMvc(mockMvc).when().get("/api/cars").then().body("carId",
                hasItems(car1.getCarId().intValue(), car2.getCarId().intValue())).and().status(HttpStatus.OK);

        verify(carService, times(1)).getAllCars();
    }

    @Test
    public void testGetCarById() throws Exception {
        when(carService.getCarDetails(car1.getCarId())).thenReturn(Optional.of(car1));

        RestAssuredMockMvc.given().mockMvc(mockMvc).when().get("/api/cars/1").then().body("carId", is(1));

        verify(carService, times(1)).getCarDetails(1L);

    }

    @Test
    public void testPostCar() throws Exception {
        // mock behaviour -> always return car1 on save
        when(carService.save(car1)).thenReturn(car1);

        RestAssuredMockMvc.given().mockMvc(mockMvc).and().contentType(ContentType.JSON).and().body(car1).when()
                .post("/api/cars").then().status(HttpStatus.CREATED).and()
                .body("maker", is("Renault")).and().body("model", is("Megane"));

        // carService.save(car1) should be called a single time
        verify(carService, times(1)).save(car1);
    }

    @Test
    public void testGetAllNissans() throws Exception {
        List<Car> nissans = new ArrayList<>(Arrays.asList(car1, car3));
        when(carService.getCarsByMaker("Nissan")).thenReturn(nissans);

        RestAssuredMockMvc.given().mockMvc(mockMvc).when().get("/api/cars?maker=Nissan").then().status(HttpStatus.OK)
                .and().body("size()", equalTo(2));

        verify(carService, times(1)).getCarsByMaker("Nissan");
    }

    @Test
    public void testGetInvalidMaker_thenReturnNotFound() throws Exception {
        when(carService.getCarsByMaker("empty")).thenReturn(new ArrayList<>());

        RestAssuredMockMvc.given().mockMvc(mockMvc).when().get("/api/cars?maker=Nissan").then()
                .status(HttpStatus.NOT_FOUND)
                .and().body(emptyArray());

        verify(carService, times(1)).getCarsByMaker("empty");
    }

    @Test
    public void testGetInvalidIDCar() throws Exception {
        when(carService.getCarDetails(-1L)).thenReturn(Optional.empty());

        RestAssuredMockMvc.given().mockMvc(mockMvc).when().get("/api/cars/-1").then()
                .status(HttpStatus.NOT_FOUND)
                .and().body(empty());

        verify(carService, times(1)).getCarDetails(-1L);
    }

    @Test
    public void testPostNullCar() throws Exception {
        when(carService.save(null)).thenReturn(null);

        RestAssuredMockMvc.given().mockMvc(mockMvc).and().contentType(ContentType.JSON).and().body("{}").when()
                .post("/api/cars").then().status(HttpStatus.BAD_REQUEST).and()
                .body(equalTo(""));

    }
}
