package com.codingbattle.controller;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.dto.SourceCodeDto;
import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompilationController {

    @Autowired
    private CompilationService compilationService;

    @PostMapping("/test")
    public TestResultDto compile(@RequestBody SourceCodeDto dto) throws Exception {
        return compilationService.compile(dto.getSource(), dto.getGameName());
    }
}
