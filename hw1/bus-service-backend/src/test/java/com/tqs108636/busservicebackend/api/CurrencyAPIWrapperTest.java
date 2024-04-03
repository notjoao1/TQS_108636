package com.tqs108636.busservicebackend.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class CurrencyAPIWrapperTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyAPIWrapper currencyAPIWrapper;

    @Test
    void testGetLatestRates_Success() {
        CurrencyResponse mockResponse = new CurrencyResponse();
        mockResponse.setRates(new HashMap<>() {
            {
                put("USD", 1.23f);
            }
        });

        when(restTemplate.getForObject(anyString(), eq(CurrencyResponse.class)))
                .thenReturn(mockResponse);

        Optional<CurrencyResponse> result = currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD");

        assertTrue(result.isPresent());
        assertEquals(1.23f, result.get().getRates().get("USD"));

        verify(restTemplate, times(1)).getForObject(anyString(), eq(CurrencyResponse.class));
    }

    @Test
    void testGetLatestRates_Failure() {
        when(restTemplate.getForObject(anyString(), eq(CurrencyResponse.class)))
                .thenThrow(new RestClientException("API Unavailable"));

        Optional<CurrencyResponse> result = currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD");

        assertFalse(result.isPresent());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(CurrencyResponse.class));
    }
}
