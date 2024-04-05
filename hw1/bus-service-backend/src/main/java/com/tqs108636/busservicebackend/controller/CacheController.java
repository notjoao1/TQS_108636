package com.tqs108636.busservicebackend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs108636.busservicebackend.api.CurrencyResponse;
import com.tqs108636.busservicebackend.cache.Cache;
import com.tqs108636.busservicebackend.dto.CacheStatsDTO;

@RestController
@RequestMapping("api/cache")
public class CacheController {
    private Cache cache;

    @Autowired
    public CacheController(Cache cache) {
        this.cache = cache;
    }

    @GetMapping("stats")
    public ResponseEntity<CacheStatsDTO> getStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    @GetMapping("cached")
    public ResponseEntity<Map<String, CurrencyResponse>> getCachedData() {
        return ResponseEntity.ok(cache.getCachedData());
    }

    @PostMapping("reset")
    public ResponseEntity<Void> resetCache() {
        cache.reset();
        return ResponseEntity.ok().build();
    }
}
