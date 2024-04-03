package com.tqs108636.busservicebackend.api;

import java.util.Optional;

public interface ICurrencyAPIWrapper {
    Optional<CurrencyResponse> getLatestRatesFromTo(String fromCurrency, String toCurrency);
}
