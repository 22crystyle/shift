package org.example;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Command(name = "file-processor", mixinStandardHelpOptions = true, description = "File content filtering utility.", version = "1.0-SNAPSHOT")
public class Main implements Runnable {
    @Option(names = {"-o", "--output"}, description = "Output directory path", defaultValue = "./")
    private String outputDir;

    @Option(names = {"-p", "--prefix"}, description = "Prefix for output files", defaultValue = "")
    private String prefix;

    @Option(names = {"-a", "--append"}, description = "Append to existing files", defaultValue = "false")
    private boolean append;

    @Option(names = {"-s", "--summary"}, description = "Generate summary information")
    private boolean summary;

    @Option(names = {"-f", "--file-info"}, description = "Analyze file information")
    private boolean fullInfo;

    @Parameters(description = "List of input files", arity = "1..*")
    private List<File> files = new ArrayList<>();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (files.isEmpty()) {
            log.error("No input files provided.");
            return;
        }

        try {
            char infoType = summary ? 's' : fullInfo ? 'f' : '\0';

            FilePatternParser parser = new FilePatternParser().parse(files, PatternName.values());
            FilePatternWriter writer = new FilePatternWriter();
            FileInfo info = new FileInfo(writer.write(parser, outputDir, prefix, append));

            if (infoType == 's') {
                info.summary();
            }

            if (infoType == 'f') {
                info.analyzeFiles();
            }
        } catch (IOException e) {
            log.error("Error processing files", e);
        }
    }
}