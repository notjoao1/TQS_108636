package com.tqs108636.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(css = ".form-inline:nth-child(1) > option:nth-child(6)")
    WebElement mxcButton;

    @FindBy(css = ".btn-primary")
    WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        PageFactory.initElements(this.driver, this);
    }

    public ChooseFlightPage clickFindFlights() {
        findFlightsButton.click();
        return new ChooseFlightPage(driver);
    }

    public void selectMexicoCity() {
        mxcButton.click();
    }

    public boolean isMexicoCitySelected() {
        return mxcButton.isSelected();
    }

}
