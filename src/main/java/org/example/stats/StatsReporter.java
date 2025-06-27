package org.example.stats;

import java.util.Map;

public class StatsReporter {
    public static void print(StatsResult result) {
        System.out.println("=== " + result.getType() + " statistics for " + result.getFileName() + " ===");
        result.getMetrics().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
