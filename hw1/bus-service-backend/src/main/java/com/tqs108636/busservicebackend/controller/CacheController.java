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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/cache")
public class CacheController {
    private Cache cache;

    @Autowired
    public CacheController(Cache cache) {
        this.cache = cache;
    }

    @Operation(summary = "Get cache usage statistics, such as cache hits and misses.")
    @ApiResponse(responseCode = "200", description = "Cache usage statistics.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CacheStatsDTO.class)) })
    @GetMapping("stats")
    public ResponseEntity<CacheStatsDTO> getStats() {
        return ResponseEntity.ok(cache.getStats());
    }

    @Operation(summary = "Get cached data as a Map between key and cached value.")
    @ApiResponse(responseCode = "200", description = "Cached data.")
    @GetMapping("cached")
    public ResponseEntity<Map<String, CurrencyResponse>> getCachedData() {
        return ResponseEntity.ok(cache.getCachedData());
    }

    @Operation(summary = "Reset cache.")
    @ApiResponse(responseCode = "200", description = "Clears the cache and usage statistics.", content = {
            @Content(mediaType = "application/json", schema = @Schema(hidden = true)) })
    @PostMapping("reset")
    public ResponseEntity<Void> resetCache() {
        cache.reset();
        return ResponseEntity.ok().build();
    }
}
