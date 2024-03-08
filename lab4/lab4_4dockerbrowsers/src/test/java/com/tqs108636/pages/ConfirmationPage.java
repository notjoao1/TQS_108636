package com.tqs108636.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    private WebDriver driver;

    @FindBy(css = ".hero-unit > h1:nth-child(1)")
    WebElement h1Heading;

    @FindBy(css = "input.btn")
    WebElement purchaseFlightButton;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String getH1Heading() {
        return h1Heading.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

}
