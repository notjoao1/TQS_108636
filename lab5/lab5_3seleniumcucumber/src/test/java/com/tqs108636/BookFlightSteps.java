package com.tqs108636;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BookFlightSteps {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        logger.info("Navigate to {}", url);
        driver = WebDriverManager.chromedriver().create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get(url);
    }

    @And("I select {string} as the departure city")
    public void selectDepartureCity(String departureCity) {
        logger.info("Select departure city - {}", departureCity);
        driver.findElement(By.cssSelector("select.form-inline:nth-child(1) > option:nth-child(6)")).click();
    }

    @And("I click Find Flights")
    public void selectFindFlights() {
        logger.info("Click find flights");
        driver.findElement(By.cssSelector("input.btn")).click();
    }

    @Then("the page subsubheader should say {string}")
    public void checkSubsubheaderContent(String expectedContent) {
        String actualContent = driver.findElement(By.cssSelector("div.container:nth-child(2) > h3:nth-child(1)"))
                .getText();
        logger.info("Check H3 header content, expected: {}, actual: {}", expectedContent, actualContent);
        assertEquals(actualContent, expectedContent);
    }

    @When("I select the flight number {int}")
    public void selectFlight(int flightNumber) {
        logger.info("Select flight number", flightNumber);
        driver.findElement(By.cssSelector(
                String.format(".table > tbody:nth-child(2) > tr:nth-child(%d) > td:nth-child(2) > input:nth-child(1)",
                        flightNumber)))
                .click();
    }

    @Then("the page subheader should say {string}")
    public void checkSubheaderContent(String expectedContent) {
        String actualContent = driver.findElement(By.cssSelector("div.container:nth-child(2) > h2:nth-child(1)"))
                .getText();
        System.out.println("cenas" + actualContent);
        logger.info("Check H2 header content, expected: {}, real {}", expectedContent, actualContent);
        assertEquals(expectedContent, actualContent);
    }

    @And("the page title should be {string}")
    public void checkPageTitle(String expectedTitle) {
        logger.info("Check page title, expected: {}, real {}", expectedTitle, driver.getTitle());

        assertEquals(driver.getTitle(), expectedTitle);
    }

    @When("I write my {string} as {string}")
    public void writeInput(String htmlId, String inputValue) {
        driver.findElement(By.id(htmlId)).sendKeys(inputValue);
    }

    @And("I write my name as {string}")
    public void writeInputInbox(String inputValue) {
        driver.findElement(By.id("inputName")).sendKeys(inputValue);
    }

    @And("I click Purchase Flight")
    public void clickPurchaseFlight() {
        logger.info("Clicked purchase flight");
        driver.findElement(By.cssSelector("input.btn")).click();
    }

    @Then("the page header should say {string}")
    public void checkPageHeaderContent(String expectedContent) {
        String actualContent = driver.findElement(By.cssSelector(".hero-unit > h1:nth-child(1)")).getText();
        logger.info("Check H1 header content, expected: {}, real {}", expectedContent, actualContent);
        assertEquals(expectedContent, actualContent);
    }

}