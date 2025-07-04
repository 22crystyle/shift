package org.example.stats;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Log4j2
public class IntegerStats implements Stats {
    private long max = Long.MIN_VALUE;
    private long min = Long.MAX_VALUE;
    private long sum = 0;
    private long count = 0;

    @Override
    public StatsResult collect(List<String> lines, Path file) {
        for (String line : lines) {
            try {
                long number = Long.parseLong(line);
                sum += number;
                max = Math.max(max, number);
                min = Math.min(min, number);
                count++;
            } catch (NumberFormatException e) {
                log.warn("{} contains unsupported data type. Skipped integer: {}", file, line);
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

        return new StatsResult(PatternName.INTEGER.getName(), file, metrics);
    }
}