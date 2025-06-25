package org.example.stats;

import java.util.Map;
import java.util.Optional;

public class StatsFactory {
    private static final Map<String, Stats> MAP = Map.of(
            "integer", new IntegerStats(),
            "double", new DoubleStats(),
            "string", new StringStats()
    );
    public static Optional<Stats> get(String type) {
        return Optional.ofNullable(MAP.get(type));
    }
}
