package com.codingbattle.compile;

import org.springframework.stereotype.Service;

@Service
public interface CompilationService {

    String compile(String code) throws Exception;
}
