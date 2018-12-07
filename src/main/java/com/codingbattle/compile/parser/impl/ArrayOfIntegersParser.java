package com.codingbattle.compile.parser.impl;

import com.codingbattle.compile.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArrayOfIntegersParser implements Parser<String, int[]> {

    @Override
    public int[] parse(String source) {
        String arr = source.substring(source.indexOf("{")+1, source.lastIndexOf("}"));
        String[] numbers = arr.split(",");
        List<Integer> listOfInts = Arrays.stream(numbers).map(Integer::parseInt).collect(Collectors.toList());
        int[] result = new int[listOfInts.size()];
        int counter =0;
        for(Integer i: listOfInts){
            result[counter]=i;
            counter++;
        }
        return result;
    }
}
