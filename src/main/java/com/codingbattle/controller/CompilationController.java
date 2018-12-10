package com.codingbattle.controller;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.dto.SourceCodeDto;
import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.Task;
import com.codingbattle.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompilationController {

    @Autowired
    private CompilationService compilationService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/test")
    public TestResultDto compile(@RequestBody SourceCodeDto dto) throws Exception {
        Task task = taskService.findById(dto.getTaskId());
        return compilationService.compile(dto.getSource(), dto.getGameName(), task);
    }
}
