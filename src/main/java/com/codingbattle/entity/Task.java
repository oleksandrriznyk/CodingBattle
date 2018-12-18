package com.codingbattle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tasks")
public class Task {

    @Id
    private String taskName;
    private String taskText;
    private List<Test> test;

    public Task() {
    }

    public Task(String taskName, String taskText, List<Test> test) {
        this.taskName = taskName;
        this.taskText = taskText;
        this.test = test;
    }

    public String getId() {
        return taskName;
    }

    public void setId(String taskName) {
        this.taskName = taskName;
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
