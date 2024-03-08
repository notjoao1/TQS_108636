package com.tqs108636.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchaseFlightPage {
    private WebDriver driver;

    @FindBy(css = "div.container:nth-child(2) > h2:nth-child(1)")
    WebElement h2Heading;

    @FindBy(css = "#inputName")
    WebElement nameInput;

    @FindBy(css = "#address")
    WebElement addressInput;

    @FindBy(css = "#city")
    WebElement cityInput;

    @FindBy(css = "#state")
    WebElement stateInput;

    @FindBy(css = "#zipCode")
    WebElement zipcodeInput;

    @FindBy(css = "#creditCardNumber")
    WebElement ccNumberInput;

    @FindBy(css = "#creditCardMonth")
    WebElement ccMonthInput;

    @FindBy(css = "#creditcardYear")
    WebElement ccYearInput;

    @FindBy(css = "#nameOnCard")
    WebElement nameOnCardInput;

    @FindBy(css = "input.btn")
    WebElement purchaseFlightButton;

    public PurchaseFlightPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    // Write to name input box
    public void name(String name) {
        nameInput.sendKeys(name);
    }

    // Write to address input box
    public void address(String address) {
        addressInput.sendKeys(address);
    }

    public void city(String city) {
        cityInput.sendKeys(city);
    }

    public void state(String state) {
        stateInput.sendKeys(state);
    }

    public void zipCode(String zipCode) {
        zipcodeInput.sendKeys(zipCode);
    }

    public void creditCardNumber(String creditCardNumber) {
        ccNumberInput.sendKeys(creditCardNumber);
    }

    public void creditCardMonth(String creditCardMonth) {
        ccMonthInput.sendKeys(creditCardMonth);
    }

    public void creditCardYear(String creditCardYear) {
        ccYearInput.sendKeys(creditCardYear);
    }

    public void nameOnCard(String nameOnCard) {
        nameOnCardInput.sendKeys(nameOnCard);
    }

    public ConfirmationPage clickPurchaseFlight() {
        purchaseFlightButton.click();
        return new ConfirmationPage(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getH2Heading() {
        return h2Heading.getText();
    }

}
