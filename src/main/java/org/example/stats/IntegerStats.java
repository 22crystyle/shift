package org.example.stats;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class IntegerStats implements Stats {
    private final Map<String, Object> metadata = new LinkedHashMap<>();
    private long max = Integer.MIN_VALUE;
    private long min = Integer.MAX_VALUE;
    private long sum = 0;
    private long count = 0;

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

        double average = count > 0 ? ((double) sum / count) : 0;
        metadata.put("Type", PatternName.INTEGER);
        metadata.put("File Name", fileName);
        metadata.put("sum", sum);
        metadata.put("max", max);
        metadata.put("min", min);
        metadata.put("average", average);
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
}
