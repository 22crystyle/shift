package org.example.stats;

import java.util.Map;

public class StatsReporter {
    public static void print(Map<String, Object> meta) {
        System.out.println("=== " + meta.get("Type") + " statistics ===");
        meta.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
