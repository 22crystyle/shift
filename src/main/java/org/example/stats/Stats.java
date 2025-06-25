package org.example.stats;

import java.util.List;
import java.util.Map;

public interface Stats {
    void collect(List<String> lines, String fileName);

    Map<String, Object> getMetadata();
}
