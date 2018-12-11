package com.codingbattle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "tasks")
public class Task {

    @Id
    private UUID id;
    private String taskText;
    private List<Test> test;

    public Task() {
    }

    public Task(UUID id, String taskText, List<Test> test) {
        this.id = id;
        this.taskText = taskText;
        this.test = test;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public List<Test> getTest() {
        return test;
    }

    public void setTest(List<Test> test) {
        this.test = test;
    }
}
