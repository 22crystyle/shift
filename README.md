# File Processing Toolkit 🔍📁

A Java CLI utility for analyzing and categorizing file contents by data type (integers, doubles, strings) with statistical reporting.

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## ✨ Features
- Auto-detection of data types (integers, doubles, strings)
- Statistical analysis (sum, avg, min/max values, string lengths)
- File content categorization with regex patterns
- Multiple output file generation
- Summary reporting with line counts
- CLI interface with picocli
- Configurable logging (Log4j2)

## 📦 Requirements
- Java 17+
- Lombok
- Maven/Gradle

## 🚀 Quick Start

### 1. Build & Run
```bash
mvn clean package
java -jar target/file-processor.jar [OPTIONS] FILES...
```

### 2. Analyze files with statistics:
Analyze files with statistics:
```bash
java -jar file-processor.jar -f file1.txt data.csv
```
Generate summary report:
```bash
java -jar file-processor.jar -s *.log
```
Process files with custom output:
```bash
java -jar file-processor.jar -o ./output/ -p "result_" -a important.log
```

## 🛠️ Command Line Options
| Option            | Description                      | Default     |
|-------------------|----------------------------------|-------------|
| `-o, --output`    | Output directory path            | Current dir |
| `-p, --prefix`    | Filename prefix for output files | ""          |
| `-a, --append`    | Append to existing files         | false       |
| `-s, --summary`   | Generate line count summary      | false       |
| `-f, --file-info` | Show detailed file analysis      | false       |

## 📂 Output Structure
Creates 3 output files for detected data types:
```
output_dir/
├── [prefix]integer.txt
├── [prefix]double.txt
└── [prefix]string.txt
```

## 📊 Analysis Examples
### Integer File Analysis
```
File: numbers.txt
    Integer Stats ->
    Sum: 150
    Max: 50
    Min: 10
    Average: 30
```
### String File Analysis
```
File: messages.log
    The total number of lines of type String: 42
    Shortest line: 3 chars
    Longest line: 256 chars
```
## 🏗️ Project Structure
```
src/main/java/org/example/
├── Main.java              # CLI entry point
├── FileInfo.java          # Statistical analysis
├── FilePatternParser.java # Content categorization
├── FilePatternWriter.java # File output handler
└── PatternName.java       # Regex pattern definitions
```
## ⚙️ Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
    </dependency>
    <dependency>
        <groupId>info.picocli</groupId>
        <artifactId>picocli</artifactId>
        <version>4.7.5</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```
## 🔧 Logging Configuration
Edit log4j2.properties to control logging:
```properties
rootLogger.level = info  # Change to warn/error for less verbose
appender.console.layout.pattern = [%level] %msg%n
```







