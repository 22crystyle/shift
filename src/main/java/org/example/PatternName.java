package org.example;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public enum PatternName {
    INTEGER("integer", Pattern.compile("^\\d+$")),
    DOUBLE("double", Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")),
    STRING("string", Pattern.compile(".*"));

    private final String name;
    private final Pattern pattern;

    PatternName(String name, Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }
}
