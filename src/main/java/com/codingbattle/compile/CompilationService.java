package com.codingbattle.compile;

import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.Task;
import org.springframework.stereotype.Service;

@Service
public interface CompilationService {

    TestResultDto compile(String code, String gameName, Task task) throws Exception;
}
