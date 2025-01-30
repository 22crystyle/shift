package org.example;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
public class FileInfo {
    private final List<File> files;

    public FileInfo(List<File> files) {
        this.files = files;
    }

    public void summary() throws IOException {
        int totalLines = 0;
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                long fileLines = reader.lines().count();
                totalLines += (int) fileLines;
                log.info("{} has {} lines", file.getName(), fileLines);
            } catch (FileNotFoundException e) {
                log.error("File not found: {}", file.getName());
            }
        }
        log.info("Total lines: {}", totalLines);
    }

    public void analyzeFiles() throws FileNotFoundException {
        for (File file : files) {
            try (Scanner scanner = new Scanner(file)) {
                List<String> lines = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
                if (lines.isEmpty()) {
                    log.error("Empty file: {}", file.getName());
                    continue;
                }
                String type = determineType(lines.getFirst());
                switch (type) {
                    case "integer" -> analyzeIntegers(lines, file.getName());
                    case "double" -> analyzeDoubles(lines, file.getName());
                    case "string" -> analyzeStrings(lines, file.getName());
                    default -> log.warn("{} contains unsupported data type", file.getName());
                }
            }
        }
    }

    private void analyzeIntegers(List<String> lines, String fileName) throws NumberFormatException {
        long sum = 0, max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        int count = 0;

        for (String line : lines) {
            try {
                long number = Long.parseLong(line);
                sum += number;
                max = Math.max(max, number);
                min = Math.min(min, number);
                count++;
            } catch (NumberFormatException e) {
                log.warn("{} contains unsupported data type. Skipped integer: {}", fileName, line);
            }
        }

        long average = count > 0 ? (sum / count) : 0;

        System.out.printf("File: %s\n\tInteger Stats ->\n\tSum: %d,\n\tMax: %d,\n\tMin: %d,\n\tAverage: %d\n",
                fileName, sum, max, min, average);
    }

    private void analyzeDoubles(List<String> lines, String fileName) throws NumberFormatException {
        double sum = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
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

        System.out.printf("File: %s\n\t" +
                "Double Stats ->\n\t" +
                "Sum: %f\n\tMax: %f\n\t" +
                "Min: %f\n\t" +
                "Average: %f\n", fileName, sum, max, min, average);
    }

    private void analyzeStrings(List<String> lines, String fileName) {
        long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
            if (line.length() < min) {
                min = line.length();
            }
        }

        System.out.printf("File: %s\n\t" +
                "The total number of lines of type String: %d\n\t" +
                "Shortest line: %d chars\n\t" +
                "Longest line: %d chars\n", fileName, lines.size(), min, max);
    }

    private static String determineType(String str) {
        if (PatternName.INTEGER.getPattern().matcher(str).matches()) {
            return PatternName.INTEGER.getName();
        }
        if (PatternName.DOUBLE.getPattern().matcher(str).matches()) {
            return PatternName.DOUBLE.getName();
        }
        return "string";
    }
}