package org.example.utils;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;
import org.example.stats.Stats;
import org.example.stats.StatsFactory;
import org.example.stats.StatsReporter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Log4j2
public class FileInfo {
    private final List<File> files;

    public FileInfo(List<File> files) {
        this.files = files;
    }

    private static String determineType(String str) {
        if (PatternName.INTEGER.getPattern().matcher(str).matches()) {
            return PatternName.INTEGER.getName();
        }
        if (PatternName.DOUBLE.getPattern().matcher(str).matches()) {
            return PatternName.DOUBLE.getName();
        }
        return PatternName.STRING.getName();
    }

    public void summary() {
        int totalLines = 0;
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                long fileLines = reader.lines().count();
                totalLines += (int) fileLines;
                log.info("{} has {} lines", file.getName(), fileLines);
            } catch (IOException e) {
                log.error("File not found: {}", file.getName());
            }
        }
        log.info("Total lines: {}", totalLines);
    }

    public void analyzeFiles() {
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
                Optional<Stats> optStats = StatsFactory.get(type);
                if (optStats.isEmpty()) {
                    log.warn("{} contains unsupported data type", file.getName());
                    continue;
                }
                Stats stats = optStats.get();
                stats.collect(lines, file.getName());
                StatsReporter.print(stats.getMetadata());
            } catch (FileNotFoundException e) {
                log.error("File not found: {}", file.getName());
            }
        }
    }
}