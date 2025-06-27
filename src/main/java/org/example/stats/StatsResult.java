package org.example.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public final class StatsResult {
    private final String type;
    private final String fileName;
    private final Map<String, Object> metrics;
}
