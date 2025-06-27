package org.example.stats;

import org.example.PatternName;

import java.util.List;
import java.util.Map;

public class StringStats implements Stats {
    long max = Long.MIN_VALUE;
    long min = Long.MAX_VALUE;

    @Override
    public StatsResult collect(List<String> lines, String fileName) {
        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
            if (line.length() < min) {
                min = line.length();
            }
        }

        Map<String, Object> metrics = Map.of(
                "Longest string", max,
                "Shortest string", min
        );

        return new StatsResult(PatternName.STRING.getName(), fileName, metrics);
    }
}
