package com.tqs108636.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReserveTripSteps {
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public WebDriver webDriver;

    @Given("the user navigates to the home page")
    public void goToHomePage() {
        webDriver = WebDriverManager.chromedriver().create();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        webDriver.get("http://localhost:5173/");
    }

    @When("the user selects {string} as the departure city")
    public void selectDepartureCity(String departureCity) {
        logger.info("Select departure city - {}", departureCity);

        webDriver.findElement(By.cssSelector(
                "div.flex:nth-child(3) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2) > option:nth-child(2)"))
                .click();
    }

    @And("the user selects {string} as the arrival city")
    public void selectArrivalCity(String arrivalCity) {
        logger.info("Select arrival city - {}", arrivalCity);

        webDriver.findElement(By.cssSelector(
                "div.flex-col:nth-child(2) > div:nth-child(1) > select:nth-child(2) > option:nth-child(4)")).click();
    }

    @And("the user selects the first trip")
    public void selectTrip() {
        logger.info("Click buy now button");

        webDriver
                .findElement(
                        By.cssSelector("div.card:nth-child(1) > div:nth-child(1) > div:nth-child(6) > a:nth-child(1)"))
                .click();
    }

    @Then("the page subheader should say {string}")
    public void assertSubheaderContent(String subheaderExpected) {
        String subheaderActual = webDriver.findElement(By.cssSelector("h2.text-4xl")).getText();

        assertEquals(subheaderExpected, subheaderActual);
    }

    @When("the user inputs {string} into his name")
    public void inputName(String name) {
        logger.info("Inputting \"{}\" into name input box", name);

        webDriver.findElement(By.cssSelector("#name")).sendKeys(name);

    }

    @And("the user selects an available seat")
    public void inputSeatNumber() {
        logger.info("Selecting available seat number...");

        // selecting 1st available seat
        webDriver.findElement(By.cssSelector(".select > option:nth-child(1)")).click();
    }

    @And("the user clicks the Confirm Details button")
    public void clickConfirmDetails() {
        logger.info("Confirming Reservation Details");

        webDriver.findElement(By.cssSelector(".btn-success")).click();
    }

    @Then("the page header should say {string}")
    public void assertPageHeaderContent_AfterReservation(String headerExpected) {
        String headerActual = webDriver.findElement(By.cssSelector("h1.text-4xl")).getText();

        assertEquals(headerExpected, headerActual);

    }

    @And("the client name should be {string}")
    public void assertClientName_AfterReservation(String clientNameExpected) {
        // it contains the prefix "Client name: "
        String clientNameActual = webDriver.findElement(By.xpath("/html/body/div/div/div[2]/div/p[2]"))
                .getText();

        assertEquals("Client Name: " + clientNameExpected, clientNameActual);
    }
}
