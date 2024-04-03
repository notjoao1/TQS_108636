package com.tqs108636.busservicebackend.api;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyAPIWrapper implements ICurrencyAPIWrapper {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.frankfurter.app";

    @Autowired
    public CurrencyAPIWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<CurrencyResponse> getLatestRatesFromTo(String fromCurrency, String toCurrency) {
        String url = String.format(BASE_URL + "/latest?from=%s&to=%s", fromCurrency, toCurrency);
        try {
            logger.error("Calling GET {}", url);
            CurrencyResponse res = restTemplate.getForObject(url, CurrencyResponse.class);
            return Optional.of(res);
        } catch (Exception e) {
            logger.error("GET {} failed", url);
            return Optional.empty();
        }
    }

}
