package org.example.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Getter
@Log4j2
public class FilePatternParser {
    private final Map<Pattern, List<String>> data = new HashMap<>();
    private List<PatternName> patternName;

    public FilePatternParser parse(List<Path> input, PatternName[] patternName) {
        this.patternName = List.of(patternName);

        for (Path file : input) {
            log.info("Parsing file: {}", file.getFileName());

            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String next;
                while ((next = reader.readLine()) != null) {
                    for (var p : patternName) {
                        if (!data.containsKey(p.getPattern())) data.put(p.getPattern(), new ArrayList<>());
                        if (p.getPattern().matcher(next).matches()) {
                            data.get(p.getPattern()).add(next);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                if (Files.isDirectory(file)) {
                    log.error(
                            "This is directory: {} Use -o flag path to set output path",
                            file.toAbsolutePath());
                } else {
                    log.error(
                            "Can't read file: {} Check reading permissions or file path",
                            file.toAbsolutePath());
                }
            }
        }
        return this;
    }
}
