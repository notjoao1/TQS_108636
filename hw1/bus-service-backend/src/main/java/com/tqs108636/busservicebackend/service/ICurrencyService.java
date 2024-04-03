package com.tqs108636.busservicebackend.service;

import java.util.Optional;

public interface ICurrencyService {
    // returns null if conversion failed (API down + cache miss)
    Optional<Float> convertFromCurrencyToCurrency(float value, String fromCurrency, String targetCurrency);
}
