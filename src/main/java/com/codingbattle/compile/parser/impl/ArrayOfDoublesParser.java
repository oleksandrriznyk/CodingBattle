package com.codingbattle.compile.parser.impl;

import com.codingbattle.compile.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArrayOfDoublesParser implements Parser<String, double[]> {

    @Override
    public double[] parse(String source) {
        String arr = source.substring(source.indexOf("{")+1, source.lastIndexOf("}"));
        String[] numbers = arr.split(",");
        List<Double> listOfDoubles = Arrays.stream(numbers).map(Double::parseDouble).collect(Collectors.toList());
        double[] result = new double[listOfDoubles.size()];
        int counter =0;
        for(Double i: listOfDoubles){
            result[counter]=i;
            counter++;
        }
        return result;
    }
}
