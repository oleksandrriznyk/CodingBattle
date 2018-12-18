package com.codingbattle.service;

import com.codingbattle.entity.Task;

public interface TaskService {

    Task findRandom();
    Task findOne(String taskName);

}
