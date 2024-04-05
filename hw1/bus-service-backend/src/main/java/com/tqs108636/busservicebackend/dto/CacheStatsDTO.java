package com.tqs108636.busservicebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CacheStatsDTO {
    private long cacheHits;
    private long cacheMisses;
}
