package com.tqs108636.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChooseFlightPage {
    private WebDriver driver;

    @FindBy(css = "div.container:nth-child(2) > h3:nth-child(1)")
    WebElement h3Heading;

    @FindBy(css = ".table > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(2) > input:nth-child(1)")
    WebElement chooseFlightButton;

    public ChooseFlightPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String getH3Heading() {
        return h3Heading.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public PurchaseFlightPage clickChooseFlightButton() {
        chooseFlightButton.click();
        return new PurchaseFlightPage(driver);
    }
}
