package org.example.stats;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class IntegerStats implements Stats {
    private long average = 0;
    private long max = Integer.MIN_VALUE;
    private long min = Integer.MAX_VALUE;
    private long sum = 0;
    private long count = 0;

    private final Map<String, Object> metadata = new LinkedHashMap<>();

    @Override
    public void collect(List<String> lines, String fileName) {
        for (String line : lines) {
            try {
                long number = Long.parseLong(line);
                sum += number;
                max = Math.max(max, number);
                min = Math.min(min, number);
                count++;
            } catch (NumberFormatException e) {
                log.warn("{} contains unsupported data type. Skipped integer: {}", fileName, line);
            }
        }

        long average = count > 0 ? (sum / count) : 0;

        System.out.printf("File: %s\n\tInteger Stats ->\n\tSum: %d,\n\tMax: %d,\n\tMin: %d,\n\tAverage: %d\n",
                fileName, sum, max, min, average);
        metadata.put("Type", "Integer");
        metadata.put("fileName", fileName);
        metadata.put("sum", String.valueOf(sum));
        metadata.put("max", String.valueOf(max));
        metadata.put("min", String.valueOf(min));
        metadata.put("average", String.valueOf(average));
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Map.of();
    }
}
