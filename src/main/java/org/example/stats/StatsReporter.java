package org.example.stats;

public class StatsReporter {
    public static void print(StatsResult result) {
        System.out.println("=== " + result.getType() + " statistics for " + result.getFile() + " ===");
        result.getMetrics().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
