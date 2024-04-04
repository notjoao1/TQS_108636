package com.tqs108636.busservicebackend.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.api.ICurrencyAPIWrapper;
import com.tqs108636.busservicebackend.service.ICurrencyService;

@Service
public class CurrencyService implements ICurrencyService {

    private ICurrencyAPIWrapper currencyAPIWrapper;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Autowired
    public CurrencyService(ICurrencyAPIWrapper currencyAPIWrapper) {
        this.currencyAPIWrapper = currencyAPIWrapper;
    }

    @Override
    public Optional<Float> convertFromCurrencyToCurrency(float value, String fromCurrency, String targetCurrency) {
        if (fromCurrency.equals(targetCurrency))
            return Optional.of(value);
        Optional<CurrencyResponse> currencyResponse = currencyAPIWrapper.getLatestRatesFromTo(fromCurrency,
                targetCurrency);

        if (currencyResponse.isEmpty()) {
            logger.error("Failed to query currency API for currency conversion {}-{}", fromCurrency, targetCurrency);
            return Optional.empty();
        }

        float rate = currencyResponse.get().getRates().get(targetCurrency);

        return Optional.of(value * rate);
    }

}
