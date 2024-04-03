package com.tqs108636.busservicebackend.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertTrue(cache.contains(KEY));
        assertEquals(cache.get(KEY), testCurrencyResponse);
    }

    @Test
    void test_TTL_Expiration() {
        String KEY = "TEST2";
        cache.put(KEY, testCurrencyResponse, 1);

        assertTrue(cache.contains(KEY));
        assertEquals(cache.get(KEY), testCurrencyResponse);

        // sleep 1s
        Awaitility.await().atMost(2, TimeUnit.SECONDS) // Adjust timeout if needed
                .until(() -> !cache.contains(KEY));

        assertFalse(cache.contains(KEY));
    }

    @Test
    void test_Read_After5Seconds_OfWrite() {
        String KEY = "TEST3";
        cache.put(KEY, testCurrencyResponse, 10);

        assertTrue(cache.contains(KEY));
        assertEquals(cache.get(KEY), testCurrencyResponse);

        // sleep 5s
        // (https://groups.google.com/g/awaitility/c/W1e5nfODGqk/m/aPfC-HjtAgAJ)
        Awaitility.await().pollDelay(5, TimeUnit.SECONDS).until(() -> true);

        assertTrue(cache.contains(KEY));
        assertEquals(cache.get(KEY), testCurrencyResponse);
    }
}
