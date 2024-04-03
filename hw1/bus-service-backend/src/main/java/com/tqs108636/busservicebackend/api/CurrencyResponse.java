package com.tqs108636.busservicebackend.api;

import java.util.Map;

import lombok.Data;

@Data
public class CurrencyResponse {
    private int amount;
    private String base; // base currency (ex: EUR)
    private String date;
    private Map<String, Float> rates;
}
