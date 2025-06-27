package org.example.stats;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.util.List;
import java.util.Map;

@Log4j2
public class DoubleStats implements Stats {

    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;
    private double sum = 0;

    @Override
    public StatsResult collect(List<String> lines, String fileName) {
        int count = 0;

        for (String line : lines) {
            try {
                double number = Double.parseDouble(line);
                sum += number;
                max = Math.max(max, number);
                min = Math.min(min, number);
                count++;
            } catch (NumberFormatException e) {
                log.warn("{} contains unsupported data type. Skipped double: {}: ", fileName, line);
            }
        }

        double average = count > 0 ? (sum / count) : 0;

        Map<String, Object> metrics = Map.of(
                "sum", sum,
                "max", max,
                "min", min,
                "average", average
        );

        return new StatsResult(PatternName.DOUBLE.getName(), fileName, metrics);
    }
}
