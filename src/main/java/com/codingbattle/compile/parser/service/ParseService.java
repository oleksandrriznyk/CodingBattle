package com.codingbattle.compile.parser.service;

public interface ParseService {

    <T> T parse(String source, Class<T> target);
}
