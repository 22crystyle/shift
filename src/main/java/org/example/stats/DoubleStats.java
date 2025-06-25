package org.example.stats;

import java.util.List;

public class DoubleStats implements Stats {
    private double average = 0;
    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;

    @Override
    public void collect(List<String> lines, String fileName) {

    }
}
