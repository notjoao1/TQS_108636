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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tqs108636.busservicebackend.cache.Cache;

@ExtendWith(MockitoExtension.class)
class CurrencyAPIWrapperTest {
    @Mock
    RestTemplate restTemplate;

    @Mock
    Cache cache;

    @InjectMocks
    CurrencyAPIWrapper currencyAPIWrapper;

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
    void testGetLatestRates_Success_FromCache() {
        CurrencyResponse mockResponse = new CurrencyResponse();
        mockResponse.setRates(new HashMap<>() {
            {
                put("USD", 1.23f);
            }
        });

        // setup mocks
        when(cache.get(anyString())).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(CurrencyResponse.class)))
                .thenReturn(mockResponse);

        Optional<CurrencyResponse> result = currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD");

        // called API, since response not yet cached
        verify(restTemplate, times(1)).getForObject(anyString(), eq(CurrencyResponse.class));

        Mockito.reset(cache, restTemplate); // reset call counter

        when(cache.get(anyString())).thenReturn(mockResponse);

        assertTrue(result.isPresent());
        assertEquals(1.23f, result.get().getRates().get("USD"));

        // cache should be used at this point
        Optional<CurrencyResponse> resultCache = currencyAPIWrapper.getLatestRatesFromTo("EUR", "USD");

        assertTrue(resultCache.isPresent());
        assertEquals(1.23f, resultCache.get().getRates().get("USD"));

        verify(cache, times(1)).get(anyString());
        verify(restTemplate, times(0)).getForObject(anyString(), eq(CurrencyResponse.class)); // cache used instead
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
