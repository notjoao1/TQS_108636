package com.tqs108636.busservicebackend.cache;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.dto.CacheStatsDTO;

@Component("cache")
public class Cache {
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private static long cacheHits = 0;
    private static long cacheMisses = 0;

    private static final Map<String, CurrencyResponse> cacheMap = new HashMap<>();
    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(4);

    public void put(String key, CurrencyResponse value, int ttlSeconds) {
        cacheMap.put(key, value);
        executor.schedule(() -> {
            logger.debug("Key {} expired after {} seconds", key, ttlSeconds);
            removeValue(key);
        }, ttlSeconds, TimeUnit.SECONDS);
    }

    public CurrencyResponse get(String key) {
        if (!cacheMap.containsKey(key)) {
            logger.info("Cache MISS for key = {}", key);
            cacheMisses++;
            return null;
        }
        logger.info("Cache HIT for key = {}", key);
        cacheHits++;
        return cacheMap.get(key);
    }

    public CacheStatsDTO getStats() {
        return new CacheStatsDTO(cacheHits, cacheMisses);
    }

    public Map<String, CurrencyResponse> getCachedData() {
        return cacheMap;
    }

    // completely clears cache and stats
    public void reset() {
        resetStats();
        cacheMap.clear();
    }

    private void resetStats() {
        cacheHits = 0;
        cacheMisses = 0;
    }

    private void removeValue(String key) {
        cacheMap.remove(key);
    }

}
