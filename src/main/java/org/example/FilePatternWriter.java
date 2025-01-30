package org.example;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Log4j2
public class FilePatternWriter {
    private final ArrayList<File> editedFiles = new ArrayList<>();

    public ArrayList<File> write(FilePatternParser parser, String output, String prefix, boolean append) throws IOException {
        PatternName[] patterns = parser.getPatternName();
        Map<Pattern, List<String>> data = parser.getData();

        for (PatternName pattern : patterns) {
            if (!(data.get(pattern.getPattern()) == null)) {
                StringBuilder sb = new StringBuilder();
                for (var j = 0; j < data.get(pattern.getPattern()).size(); j++) {
                    sb.append(data.get(pattern.getPattern()).get(j)).append("\n");
                }
                try (FileOutputStream fos = new FileOutputStream(output + prefix + pattern.getName(), append)) {
                    editedFiles.add(new File(output + prefix + pattern.getName()));
                    fos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                    log.info("File saved at: {}", output + prefix + pattern.getName());
                } catch (FileNotFoundException e) {
                    log.error("Output path not found");
                    break;
                }
            }
        }

        return editedFiles;
    }
}