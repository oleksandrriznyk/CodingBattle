package com.codingbattle.compile.parser.service.impl;

import com.codingbattle.compile.parser.ParserHolders;
import com.codingbattle.compile.parser.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseServiceImpl implements ParseService {

    @Autowired
    private ParserHolders parserHolders;
    @Override
    public <T> T parse(String source, Class<T> target) {
        return (T)parserHolders.getParserMap().get(target).parse(source);
    }
}
