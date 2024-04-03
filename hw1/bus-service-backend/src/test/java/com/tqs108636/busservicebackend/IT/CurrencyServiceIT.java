package com.tqs108636.busservicebackend.IT;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.tqs108636.busservicebackend.service.ICurrencyService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CurrencyServiceIT {
    @Autowired
    ICurrencyService currencyService;

    @Test
    void testConvert_And_ExternalAPI_IsUp() {
        Optional<Float> convertedValue = currencyService.convertFromCurrencyToCurrency(10.0f, "EUR", "USD");

        assertTrue(convertedValue.isPresent());
        // cannot make assertions about non deterministic conversion rates
    }
}
