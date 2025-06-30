package org.example.utils;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;
import org.example.stats.Stats;
import org.example.stats.StatsFactory;
import org.example.stats.StatsReporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
public class FileInfo {
    private final List<Path> files;

    public FileInfo(List<Path> files) {
        this.files = files;
    }

    private static String determineType(String str) {
        for (PatternName patternName : PatternName.values()) {
            if (patternName.getPattern().matcher(str).matches()) {
                return patternName.getName();
            }
        }

        return PatternName.STRING.getName();
    }

    public void summary() {
        long totalLines = 0;
        for (Path file : files) {
            try (Stream<String> stream = Files.lines(file, StandardCharsets.UTF_8)) {
                long fileLines = stream.count();
                totalLines += fileLines;
                System.out.printf("%s has %d lines\n", file.getFileName(), fileLines);
            } catch (IOException e) {
                logFileNotFound(file, e);
            }
        }
        System.out.printf("Total lines: %d\n", totalLines);
    }

    public void analyzeFiles() {
        for (Path file : files) {
            try {
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                if (lines.isEmpty()) {
                    log.error("Empty file: {}", file.getFileName());
                    continue;
                }
                String type = determineType(lines.getFirst());
                Optional<Stats> optStats = StatsFactory.get(type);
                if (optStats.isEmpty()) {
                    log.warn("{} contains unsupported data type", file.getFileName());
                    continue;
                }
                Stats stats = optStats.get();
                StatsReporter.print(stats.collect(lines, file));
            } catch (FileNotFoundException e) {
                logFileNotFound(file, e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void logFileNotFound(Path file, Exception e) {
        log.error("File not found: {}", file.getFileName(), e);
    }
}