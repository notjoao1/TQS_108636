package com.tqs108636.busservicebackend.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tqs108636.busservicebackend.api.CurrencyResponse;

class CacheTest {
    Cache cache = new Cache();

    CurrencyResponse testCurrencyResponse;

    @BeforeEach
    void setup() {
        testCurrencyResponse = new CurrencyResponse();
        testCurrencyResponse.setBase("asdfasdfasdfasdfasdfasdfasdfasdfasdf");
    }

    @Test
    void testRead_After_Write() {
        String KEY = "TEST";
        cache.put(KEY, testCurrencyResponse, 1);

        assertEquals(cache.get(KEY), testCurrencyResponse);
    }

    @Test
    void test_TTL_Expiration() {
        String KEY = "TEST2";
        cache.put(KEY, testCurrencyResponse, 1);

        assertEquals(cache.get(KEY), testCurrencyResponse);

        // sleep 1s
        Awaitility.await().atMost(2, TimeUnit.SECONDS) // Adjust timeout if needed
                .until(() -> cache.get(KEY) == null);

        assertNull(cache.get(KEY));
    }

    @Test
    void test_Read_After5Seconds_OfWrite() {
        String KEY = "TEST3";
        cache.put(KEY, testCurrencyResponse, 10);

        assertEquals(cache.get(KEY), testCurrencyResponse);

        // sleep 5s
        // (https://groups.google.com/g/awaitility/c/W1e5nfODGqk/m/aPfC-HjtAgAJ)
        Awaitility.await().pollDelay(5, TimeUnit.SECONDS).until(() -> true);

        assertEquals(cache.get(KEY), testCurrencyResponse);
    }
}
