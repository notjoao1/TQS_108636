package com.tqs108636.busservicebackend.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.dto.CacheStatsDTO;
import com.tqs108636.busservicebackend.model.Location;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_it.properties")
class CacheControllerIT {
    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testGetCacheStats() {
        // make sure stats are 0
        restTemplate.exchange(
                "/api/cache/resetStats",
                HttpMethod.POST,
                null,
                Void.class);

        // one conversion per trip, and first conversion to USD will cache the rate
        // there are a total of 8 trips
        restTemplate.exchange(
                "/api/trips?currency=USD",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        ResponseEntity<CacheStatsDTO> response = restTemplate.exchange("/api/cache/stats", HttpMethod.GET, null,
                CacheStatsDTO.class);

        CacheStatsDTO cacheStats = response.getBody();

        // first conversion missed the cache and then cached the EUR-USD conversion rate
        assertEquals(1, cacheStats.getCacheMisses());
        // all other 7 trips will hit the cache
        assertEquals(7, cacheStats.getCacheHits());
    }

    @Test
    void testGetCachedData() {
        // should cache "EUR" - "USD" currency API response
        restTemplate.exchange(
                "/api/trips?currency=USD",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        ResponseEntity<Map<String, CurrencyResponse>> response = restTemplate.exchange("/api/cache/cached",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Map<String, CurrencyResponse>>() {

                });

        Map<String, CurrencyResponse> cachedData = response.getBody();

        assertTrue(cachedData.containsKey("LATEST:EUR-USD"));
        assertEquals("EUR", cachedData.get("LATEST:EUR-USD").getBase());
    }

    @Test
    void testPost_ResetCacheStats() {
        restTemplate.exchange(
                "/api/trips?currency=USD",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {
                });

        ResponseEntity<CacheStatsDTO> response = restTemplate.exchange("/api/cache/stats", HttpMethod.GET, null,
                CacheStatsDTO.class);

        CacheStatsDTO cacheStats = response.getBody();

        assertNotEquals(0, cacheStats.getCacheMisses());
        assertNotEquals(0, cacheStats.getCacheHits());

        restTemplate.exchange(
                "/api/cache/resetStats",
                HttpMethod.POST,
                null,
                Void.class);

        ResponseEntity<CacheStatsDTO> responseAfterReset = restTemplate.exchange("/api/cache/stats", HttpMethod.GET,
                null,
                CacheStatsDTO.class);

        // stats should be 0 now
        CacheStatsDTO cacheStatsAfterReset = responseAfterReset.getBody();

        assertEquals(0, cacheStatsAfterReset.getCacheMisses());
        assertEquals(0, cacheStatsAfterReset.getCacheHits());
    }
}
