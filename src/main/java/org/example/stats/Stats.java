package org.example.stats;

import java.util.List;

public interface Stats {
    StatsResult collect(List<String> lines, String fileName);
}
