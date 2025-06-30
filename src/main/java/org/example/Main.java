package org.example;

import lombok.extern.log4j.Log4j2;
import org.example.utils.FileInfo;
import org.example.utils.FilePatternParser;
import org.example.utils.FilePatternWriter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Command(name = "file-processor", mixinStandardHelpOptions = true, description = "File content filtering utility.", version = "1.0-SNAPSHOT")
public class Main implements Runnable {
    @Option(names = {"-o", "--output"}, description = "Output directory path", defaultValue = "./")
    private Path outDir;

    @Option(names = {"-p", "--prefix"}, description = "Prefix for output files", defaultValue = "")
    private String prefix;

    @Option(names = {"-a", "--append"}, description = "Append to existing files", defaultValue = "false")
    private boolean append;

    @Option(names = {"-s", "--summary"}, description = "Generate summary information")
    private boolean summary;

    @Option(names = {"-f", "--file-info"}, description = "Analyze file information")
    private boolean fullInfo;

    @Parameters(description = "List of input files", arity = "1..*")
    private List<Path> files = new ArrayList<>();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (!Files.exists(outDir)) {
            log.error("Output path '{}' don't exists", outDir);
            System.exit(1);
        }

        if (files.isEmpty()) {
            log.error("No input files provided.");
            return;
        }

        char infoType = summary ? 's' : fullInfo ? 'f' : '\0';

        FilePatternParser parser = new FilePatternParser().parse(files, PatternName.values());
        if (parser.getData().values().stream().allMatch(List::isEmpty)) {
            log.error("No data processed. Check input files.");
            return;
        }
        FileInfo info = null;

        FilePatternWriter writer = new FilePatternWriter();
        try {
            info = new FileInfo(writer.write(parser, outDir, prefix, append));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (infoType == 's') {
            info.summary();
        }

        if (infoType == 'f') {
            info.analyzeFiles();
        }
    }
}