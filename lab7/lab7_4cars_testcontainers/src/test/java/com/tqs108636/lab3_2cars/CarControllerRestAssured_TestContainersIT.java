package com.tqs108636.lab3_2cars;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.tqs108636.lab3_2cars.data.Car;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarControllerRestAssured_TestContainersIT {
    @LocalServerPort
    int serverPort;

    // Couldnt find any other solution to Connection Refused exception
    @BeforeEach
    void setup() {
        RestAssured.port = serverPort;
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")).withUsername("test").withPassword("test")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    public void testGetAllCars() throws Exception {
        given().when().get("/api/cars").then().body("maker",
                hasItems("Renault", "Nissan")).and().body("model", hasItems("Megane", "Clio", "350Z")).and()
                .statusCode(is(HttpStatus.OK));
    }

    @Test
    public void testGetCarById() throws Exception {
        given().when().get("/api/cars/1").then().body("carId", is(1));
    }

    @Test
    public void testPostCar() throws Exception {
        Car carToPost = new Car("Ferrari", "F40");

        given().and().contentType(ContentType.JSON).and().body(carToPost).when()
                .post("/api/cars").then().statusCode(is(HttpStatus.CREATED)).and()
                .body("maker", is("Ferrari")).and().body("model", is("F40"));

    }

    @Test
    public void testGetAllNissans() throws Exception {
        given().when().get("/api/cars?maker=Nissan").then().statusCode(is(HttpStatus.OK))
                .and().body("size()", equalTo(2)).and().body("maker", is("Nissan"));
    }

    @Test
    public void testGetInvalidMaker_thenReturnNotFound() throws Exception {
        given().when().get("/api/cars?maker=Opel").then()
                .statusCode(is(HttpStatus.NOT_FOUND))
                .and().body(emptyArray());
    }

    @Test
    public void testGetInvalidIDCar() throws Exception {
        given()
                .when()
                .get("/api/cars/-1")
                .then()
                .statusCode(is(HttpStatus.NOT_FOUND))
                .and().body(empty());
    }

    @Test
    public void testPostNullCar() throws Exception {
        given().and().contentType(ContentType.JSON).and().body("{}").when()
                .post("/api/cars").then().statusCode(is(HttpStatus.BAD_REQUEST)).and()
                .body(equalTo(""));

    }
}
