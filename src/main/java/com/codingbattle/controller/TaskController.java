package com.codingbattle.controller;

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
}
