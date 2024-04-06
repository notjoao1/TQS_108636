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

public class CheckReservationDetailsSteps {
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public WebDriver webDriver;

    @Given("the user navigates to the reservation details page")
    public void goToReservationPage() {
        webDriver = WebDriverManager.chromedriver().create();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        webDriver.get("http://localhost:5173/reservation_details");
    }

    @When("the user inputs his reservation token equal to {string}")
    public void inputReservationtoken(String reservationToken) {
        logger.info("Inputting \"{}\" into name reservation token input box", reservationToken);

        webDriver.findElement(By.cssSelector(".input")).sendKeys(reservationToken);
    }

    @And("the user clicks the Confirm Details button to confirm the token")
    public void clickConfirmDetails() {
        logger.info("Confirming Reservation Token");

        webDriver.findElement(By.cssSelector(".btn-success")).click();
    }

    @Then("the reservation details should be shown")
    public void reservationDetails() {
        String pageHeader = webDriver.findElement(By.cssSelector(".text-4xl")).getText();

        assertEquals("Reservation Details", pageHeader);
    }

    @And("the reservation token should be {string}")
    public void assertReservationToken(String reservationToken) {
        // contains prefix "Reservation Token: "
        String reservationTokenExpected = webDriver.findElement(By.cssSelector("p.text-xl:nth-child(1)")).getText();

        assertEquals(reservationTokenExpected, "Reservation Token: " + reservationToken);

    }

    @And("the found client name should be {string}")
    public void assertClientName_AfterReservation(String clientNameExpected) {
        // it contains the prefix "Client name: "
        String clientNameActual = webDriver.findElement(By.cssSelector("p.text-xl:nth-child(2)"))
                .getText();

        assertEquals("Client Name: " + clientNameExpected, clientNameActual);
    }

}
