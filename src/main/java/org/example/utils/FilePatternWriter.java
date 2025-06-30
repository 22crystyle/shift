package org.example.utils;

import lombok.extern.log4j.Log4j2;
import org.example.PatternName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Log4j2
public class FilePatternWriter {
    private final List<Path> editedFiles = new ArrayList<>();

    public List<Path> write(FilePatternParser parser,
                            Path outDir,
                            String prefix,
                            boolean append) throws IOException {
        List<PatternName> patterns = parser.getPatternName();
        Map<Pattern, List<String>> data = parser.getData();

        for (PatternName pattern : patterns) {
            Path outPath = outDir.resolve(prefix + pattern.getName() + ".txt");
            List<String> lines = data.get(pattern.getPattern());
            if (lines == null || lines.isEmpty()) {
                log.warn("No data found for pattern '{}', file will not be created.", pattern.getName());
                continue;
            }
            OpenOption[] options = append ?
                    new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND} :
                    new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

            Files.write(outPath, lines, StandardCharsets.UTF_8, options);
            editedFiles.add(outPath);
        }

        return editedFiles;
    }
}