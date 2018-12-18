package com.codingbattle.controller;

import com.codingbattle.entity.Task;
import com.codingbattle.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private TaskService taskService;

    @GetMapping("/taskName")
    public Task getTask(@PathVariable("taskName") String taskName, @PathVariable("sessionId") String sessionId) {
        return taskService.findOne(taskName);
    }



}
