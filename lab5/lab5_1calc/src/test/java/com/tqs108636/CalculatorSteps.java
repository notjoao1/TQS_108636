package com.tqs108636;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {
    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Calculator calc;

    private Exception exceptionThrown;

    @Given("a calculator I just turned on")
    public void setup() {
        log.info("SETUP - Creating calculator");
        calc = new Calculator();
        exceptionThrown = null;
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I subtract {int} to {int}")
    public void substract(int arg1, int arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @When("I divide {int} by {int}")
    public void I_divide_by(int i, int i2) {
        try {
            log.debug("Dividing {} by {}", i, i2);
            calc.push(i);
            calc.push(i2);
            calc.push("/");
        } catch (IllegalArgumentException e) {
            log.debug("IllegalArgumentException Caught: {}", e.getMessage());
            exceptionThrown = e;
        }
    }

    @Then("an IllegalArgumentException is thrown")
    public void an_IllegalArgumentException_is_thrown() {
        assertEquals(exceptionThrown.getClass().toString(), "class java.lang.IllegalArgumentException");
        assertEquals(exceptionThrown.getMessage(), "Division by 0");
    }

    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} by {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

}
