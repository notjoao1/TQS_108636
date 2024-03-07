package com.tqs108636;

// Generated by Selenium IDE
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.By;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

@ExtendWith(SeleniumJupiter.class)
public class SelectAndBuyATripBlazeTest {
  JavascriptExecutor js;

  @Test
  public void selectAndBuyATripBlaze(ChromeDriver driver) {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(1912, 1044));
    {
      WebElement dropdown = driver.findElement(By.name("fromPort"));
      dropdown.findElement(By.xpath("//option[. = 'Mexico City']")).click();
    }
    driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(6)")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    driver.findElement(By.cssSelector("h2")).click();
    assertThat(driver.findElement(By.cssSelector("h2")).getText(),
        is("Your flight from TLV to SFO has been reserved."));
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).sendKeys("Test1");
    driver.findElement(By.id("address")).sendKeys("Test2");
    driver.findElement(By.id("city")).sendKeys("Test3");
    driver.findElement(By.id("state")).sendKeys("Test4");
    driver.findElement(By.id("zipCode")).sendKeys("Test5");
    driver.findElement(By.id("creditCardNumber")).sendKeys("Test6");
    driver.findElement(By.id("nameOnCard")).sendKeys("Test7");
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Thank you for your purchase today!"));
    assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
  }
}
