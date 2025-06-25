package org.example.stats;

import org.example.PatternName;

import java.util.Map;
import java.util.Optional;

public class StatsFactory {
    private static final Map<String, Stats> MAP = Map.of(
            PatternName.INTEGER.getName(), new IntegerStats(),
            PatternName.DOUBLE.getName(), new DoubleStats(),
            PatternName.STRING.getName(), new StringStats()
    );

    public static Optional<Stats> get(String type) {
        return Optional.ofNullable(MAP.get(type));
    }
}
