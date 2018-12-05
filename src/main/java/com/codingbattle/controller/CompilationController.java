package com.codingbattle.controller;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.dto.SourceCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompilationController {

    @Autowired
    private CompilationService compilationService;

    @PostMapping("/test")
    public String compile(@RequestBody SourceCodeDto dto) throws Exception {
        return compilationService.compile(dto.getSource(), dto.getGameName());
    }
}
