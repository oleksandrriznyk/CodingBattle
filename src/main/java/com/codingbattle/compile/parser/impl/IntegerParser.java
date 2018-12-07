package com.codingbattle.compile.parser.impl;

import com.codingbattle.compile.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class IntegerParser implements Parser<String, Integer> {

    @Override
    public Integer parse(String source) {
        return Integer.parseInt(source);
    }
}
