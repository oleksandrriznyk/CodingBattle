package com.codingbattle.service;

import com.codingbattle.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task findRandom();

    Task findById(String taskId);

    Task save(Task task);

    List<Task> findAll();

    void delete(String taskId);
}
