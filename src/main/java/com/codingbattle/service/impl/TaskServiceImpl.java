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

    @Override
    public Task findById(String taskId) {
        return taskRepository.findOne(taskId);
    }

    @Override
    public Task save(Task task) {
        Task tempTask = taskRepository.findOne(task.getId());
        if(tempTask!=null){
            tempTask.setInputType(task.getInputType());
            tempTask.setMethodName(task.getMethodName());
            tempTask.setStartCode(task.getStartCode());
            tempTask.setTaskText(task.getTaskText());
            tempTask.setTest(task.getTest());
            return taskRepository.save(tempTask);
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void delete(String taskId) {
        taskRepository.delete(taskId);
    }
}
