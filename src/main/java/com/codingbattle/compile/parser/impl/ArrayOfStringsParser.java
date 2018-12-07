package com.codingbattle.compile.parser.impl;

import com.codingbattle.compile.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class ArrayOfStringsParser implements Parser<String, String[]> {
    @Override
    public String[] parse(String source) {
        String arr = source.substring(source.indexOf("{")+1, source.lastIndexOf("}"));
        return arr.split(",");
    }
}
