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
./gradlew build
java -jar build/libs/shift.jar [OPTIONS] FILES...
```

### 2. Analyze files with statistics:
Analyze files with statistics:
```bash
java -jar shift.jar -f in1.txt in2.txt
```
Generate summary report:
```bash
java -jar shift.jar -s in1.txt
```
Process files with custom output:
```bash
java -jar shift.jar -o ./output/ -p "result_" -a in1.txt in2.txt
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
=== integer statistics for .\integer.txt ===
max: 100500
average: 50273
min: 45
count: 2
sum: 100545
```
### String File Analysis
```
=== string statistics for .\string.txt ===
The total number of lines of type String: 4
Shortest line: 6
Longest line: 26
```
## ⚙️ Dependencies
```build.gradle
dependencies {
        implementation("org.apache.logging.log4j:log4j-core:2.24.3")
        implementation("org.apache.logging.log4j:log4j-api:2.24.3")
        implementation("info.picocli:picocli:4.7.6")
        annotationProcessor("info.picocli:picocli-codegen:4.7.6")

        compileOnly("org.projectlombok:lombok:1.18.36")
        annotationProcessor("org.projectlombok:lombok:1.18.36")
}
```
## 🔧 Logging Configuration
Edit log4j2.properties to control logging:
```properties
rootLogger.level = info  # Change to warn/error for less verbose
appender.console.layout.pattern = [%level] %msg%n
```







