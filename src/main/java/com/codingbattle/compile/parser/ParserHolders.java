package com.codingbattle.compile.parser;

import com.codingbattle.compile.parser.impl.ArrayOfIntegersParser;
import com.codingbattle.compile.parser.impl.IntegerParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParserHolders {

    private Map<Class, Parser> parserMap;

    @PostConstruct
    private void init(){
        parserMap = new HashMap<>();
        parserMap.put(int[].class, new ArrayOfIntegersParser());
        parserMap.put(int.class, new IntegerParser());
    }

    public Map<Class, Parser> getParserMap() {
        return parserMap;
    }
}
