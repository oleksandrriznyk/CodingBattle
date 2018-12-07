package com.codingbattle.compile;

import com.codingbattle.dto.TestResultDto;
import org.springframework.stereotype.Service;

@Service
public interface CompilationService {

    TestResultDto compile(String code, String gameName) throws Exception;
}
