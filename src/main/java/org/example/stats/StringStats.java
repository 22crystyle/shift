package org.example.stats;

import org.example.PatternName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringStats implements Stats {
    private final Map<String, Object> metadata = new LinkedHashMap<>();

    long max = Long.MIN_VALUE;
    long min = Long.MAX_VALUE;

    @Override
    public void collect(List<String> lines, String fileName) {
        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
            if (line.length() < min) {
                min = line.length();
            }
        }

        metadata.put("Type", PatternName.STRING);
        metadata.put("File Name", fileName);
        metadata.put("Longest line", max);
        metadata.put("Shortest line", min);
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
}
