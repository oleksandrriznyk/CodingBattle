package com.codingbattle.compile.parser.impl;

import com.codingbattle.compile.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ListOfStringsParser implements Parser<String, ArrayList<String>> {

    @Override
    public ArrayList<String> parse(String source) {
        String arr = source.substring(source.indexOf("{")+1, source.lastIndexOf("}"));
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(arr.split(",")));
        return strings;
    }
}
