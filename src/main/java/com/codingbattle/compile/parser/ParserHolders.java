package com.codingbattle.compile.parser;

import com.codingbattle.compile.TypeManager;
import com.codingbattle.compile.parser.impl.ArrayOfIntegersParser;
import com.codingbattle.compile.parser.impl.IntegerParser;
import com.codingbattle.compile.parser.impl.ListOfStringsParser;
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
        parserMap.put(TypeManager.getArrayListOfStringClass(), new ListOfStringsParser());
    }

    public Map<Class, Parser> getParserMap() {
        return parserMap;
    }
}
