package com.codingbattle.service;

import com.codingbattle.entity.Task;

import java.util.List;

public interface TaskService {

    Task findRandom();
    Task findOne(String taskName);


    Task findById(String taskId);

    Task save(Task task);

    List<Task> findAll();

    void delete(String taskId);
}
