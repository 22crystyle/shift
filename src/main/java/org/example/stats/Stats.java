package org.example.stats;

import java.nio.file.Path;
import java.util.List;

public interface Stats {
    StatsResult collect(List<String> lines, Path file);
}
