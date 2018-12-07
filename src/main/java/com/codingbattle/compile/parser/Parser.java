package com.codingbattle.compile.parser;



@FunctionalInterface
public interface Parser<String, T> {

    T parse(String source);
}
