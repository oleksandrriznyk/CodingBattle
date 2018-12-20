package com.codingbattle.dto;

import com.codingbattle.entity.Task;

public class TaskDto {

    private Task task;
    private String sessionId;

    public TaskDto() {
    }

    public TaskDto(Task task, String sessionId) {
        this.task = task;
        this.sessionId = sessionId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
