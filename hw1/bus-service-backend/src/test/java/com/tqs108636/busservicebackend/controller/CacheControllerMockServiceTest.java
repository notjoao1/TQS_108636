package com.tqs108636.busservicebackend.controller;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.cache.Cache;
import com.tqs108636.busservicebackend.dto.CacheStatsDTO;

@WebMvcTest(CacheController.class)
class CacheControllerMockServiceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    Cache cache;

    @Test
    void testGetStats() throws Exception {
        when(cache.getStats()).thenReturn(new CacheStatsDTO(123, 4321));

        mockMvc.perform(get("/api/cache/stats")).andExpectAll(
                status().isOk(),
                jsonPath("$.cacheHits", is(123)),
                jsonPath("$.cacheMisses", is(4321)));

        verify(cache, times(1)).getStats();
    }

    @Test
    void testGetCachedData() throws Exception {
        CurrencyResponse currResponse1, currResponse2;
        currResponse1 = new CurrencyResponse();
        currResponse1.setBase("KRW");
        currResponse2 = new CurrencyResponse();
        currResponse2.setBase("MXN");

        Map<String, CurrencyResponse> mockCacheMap = new HashMap<>();
        mockCacheMap.put("TEST1", currResponse1);
        mockCacheMap.put("TEST2", currResponse2);

        when(cache.getCachedData()).thenReturn(mockCacheMap);

        mockMvc.perform(get("/api/cache/cached")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasKey("TEST1")),
                jsonPath("$.TEST1.base", is("KRW")),
                jsonPath("$", hasKey("TEST2")),
                jsonPath("$.TEST2.base", is("MXN")));

        verify(cache, times(1)).getCachedData();

    }
}
