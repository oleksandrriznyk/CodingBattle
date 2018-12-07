package com.codingbattle.service.impl;

import com.codingbattle.entity.Task;
import com.codingbattle.repository.TaskRepository;
import com.codingbattle.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findRandom() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.get((int)(Math.random()*allTasks.size()));
    }
}
