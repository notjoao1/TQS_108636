package com.tqs108636.busservicebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.api.ICurrencyAPIWrapper;
import com.tqs108636.busservicebackend.service.impl.CurrencyService;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceMockAPITest {
    @Mock
    ICurrencyAPIWrapper currencyAPIWrapper;

    @InjectMocks
    CurrencyService currencyService;

    @Test
    void testConvertFromEUR_To_USD() {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setAmount(1);
        currencyResponse.setBase("EUR");
        currencyResponse.setDate("");
        currencyResponse.setRates(new HashMap<String, Float>() {
            {
                put("USD", 0.5f);
            }
        });

        when(currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD")).thenReturn(Optional.of(currencyResponse));

        Optional<Float> convertedValue = currencyService.convertFromCurrencyToCurrency(10.0f, "EUR", "USD");

        assertTrue(convertedValue.isPresent());
        assertEquals(10.0f * 0.5f, convertedValue.get());

        verify(currencyAPIWrapper, times(1)).getLatestRatesFromTo("EUR", "USD");
    }

    @Test
    void testConvertFromEur_To_USD_WithAPIDown() {
        when(currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD")).thenReturn(Optional.empty());

        Optional<Float> convertedValue = currencyService.convertFromCurrencyToCurrency(10.0f, "EUR", "USD");

        assertTrue(convertedValue.isEmpty());

        // verify you tried
        verify(currencyAPIWrapper, times(1)).getLatestRatesFromTo("EUR", "USD");
    }
}
