package org.example;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Getter
@Log4j2
public class FilePatternParser {
    private final Map<Pattern, List<String>> data = new HashMap<>();
    private PatternName[] patternName;

    public FilePatternParser parse(List<File> input, PatternName[] patternName) throws IOException {
        this.patternName = patternName;

        for (File file : input) {
            log.info("Parsing file: {}", file.getAbsolutePath());

            try (Scanner reader = new Scanner(file)) {
                while (reader.hasNextLine()) {
                    String next = reader.nextLine();
                    for (var p : patternName) {
                        if (!data.containsKey(p.getPattern())) data.put(p.getPattern(), new ArrayList<>());
                        if (p.getPattern().matcher(next).matches()) {
                            data.get(p.getPattern()).add(next);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                if (file.isDirectory()) {
                    log.error(
                            "This is directory: {} Use -o flag path to set output path",
                            file.getAbsolutePath());
                } else {
                    log.error(
                            "Can't read file: {} Check reading permissions or file path",
                            file.getAbsolutePath());
                }
            }
        }
        return this;
    }
}
