package com.tqs108636;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

class RestAssuredTests {

    @Test
    void testGetAllTODOS_IsAvailable() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void testGetTODO4_TitleIsEtPorroTempora() {
        when().get("https://jsonplaceholder.typicode.com/todos/4").then().body("title", equalTo("et porro tempora"));
    }

    @Test
    void testGetAllTodos_IncludesIDS_198_199() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().body("id", hasItems(198, 199));
    }

    @Test
    void testGetAllTodos_LessThan2SecondsTimeTaken() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().time(lessThan(2L), TimeUnit.SECONDS);
    }
}
