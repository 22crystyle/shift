package org.example.stats;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Log4j2
public class DoubleStats implements Stats {

    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;
    private double sum = 0;
    private int count = 0;

    @Override
    public StatsResult collect(List<String> lines, Path file) {

        for (String line : lines) {
            try {
                double number = Double.parseDouble(line);
                sum += number;
                max = Math.max(max, number);
                min = Math.min(min, number);
                count++;
            } catch (NumberFormatException e) {
                log.warn("{} contains unsupported data type. Skipped double: {}: ", file, line);
            }
        }

        BigDecimal average;
        if (count == 0) {
            average = BigDecimal.ZERO;
        } else if (count == 1) {
            average = BigDecimal.valueOf(sum);
        } else {
            average = BigDecimal.valueOf(sum)
                    .divide(BigDecimal.valueOf(count), 0, RoundingMode.HALF_UP)
                    .stripTrailingZeros();
        }

        Map<String, Object> metrics = Map.of(
                "sum", sum,
                "max", max,
                "min", min,
                "average", average,
                "count", count
        );

        return new StatsResult(PatternName.DOUBLE.getName(), file, metrics);
    }
}
