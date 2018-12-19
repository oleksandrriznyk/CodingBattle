package com.codingbattle.controller;

import com.codingbattle.entity.Task;
import com.codingbattle.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.codingbattle.compile.ImportManager;
import com.codingbattle.dto.ImportsDto;
import com.codingbattle.entity.Task;
import com.codingbattle.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ImportManager importManager;
    private TaskService taskService;

    @GetMapping("/taskName")
    public Task getTask(@PathVariable("taskName") String taskName, @PathVariable("sessionId") String sessionId) {
        return taskService.findOne(taskName);
    }



    @GetMapping("/{taskId}")
    public Task find(@PathVariable("taskId") String taskId){
        return taskService.findById(taskId);
    }

    @PostMapping("/save")
    public Task save(@RequestBody Task task){
        return taskService.save(task);
    }

    @GetMapping("/all")
    public List<Task> findAll(){
        return taskService.findAll();
    }

    @GetMapping("/delete/{taskId}")
    public void delete(@PathVariable("taskId") String taskId){
        taskService.delete(taskId);
    }

    @PostMapping("/imports")
    public ImportsDto addImports(@RequestBody ImportsDto importsDto){
        importManager.addImport(importsDto.getTaskId(), importsDto.getImports());
        return importsDto;
    }
}
