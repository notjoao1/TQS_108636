package com.tqs108636.busservicebackend.api;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tqs108636.busservicebackend.cache.Cache;

@Service
public class CurrencyAPIWrapper implements ICurrencyAPIWrapper {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.frankfurter.app";

    private Cache cache;

    @Autowired
    public CurrencyAPIWrapper(RestTemplate restTemplate, Cache cache) {
        this.restTemplate = restTemplate;
        this.cache = cache;
    }

    @Override
    public Optional<CurrencyResponse> getLatestRatesFromTo(String fromCurrency, String toCurrency) {
        logger.info("Getting latest rates from {} to {}", fromCurrency, toCurrency);
        String url = String.format(BASE_URL + "/latest?from=%s&to=%s", fromCurrency, toCurrency);
        try {
            // check cache
            String cacheKey = "LATEST:" + fromCurrency + "-" + toCurrency; // ex: LATEST:EUR-USD
            CurrencyResponse cachedValue = cache.get(cacheKey);
            if (cachedValue != null) {
                return Optional.of(cachedValue);
            }
            logger.info("Calling GET {}", url);
            CurrencyResponse res = restTemplate.getForObject(url, CurrencyResponse.class);
            cache.put(cacheKey, res, 60 * 60); // cache for 1h
            return Optional.of(res);
        } catch (Exception e) {
            logger.error("GET {} failed", url);
            return Optional.empty();
        }
    }

}
