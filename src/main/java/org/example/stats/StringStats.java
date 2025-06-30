package org.example.stats;

import org.example.PatternName;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class StringStats implements Stats {
    long max = Long.MIN_VALUE;
    long min = Long.MAX_VALUE;
    long count = 0;

    @Override
    public StatsResult collect(List<String> lines, Path file) {
        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
            if (line.length() < min) {
                min = line.length();
            }
            count++;
        }

        Map<String, Object> metrics = Map.of(
                "Longest line", max,
                "Shortest line", min,
                "The total number of lines of type String", count
        );

        return new StatsResult(PatternName.STRING.getName(), file, metrics);
    }
}
