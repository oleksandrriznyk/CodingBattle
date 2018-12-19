package com.codingbattle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String taskName;
    private String taskText;
    private List<Test> test;
    private String inputType;
    private String methodName;
    private String startCode;

    public Task() {
    }

    public Task(String id, String taskText, List<Test> test, String inputType, String methodName, String startCode) {
        this.id = id;
        this.taskText = taskText;
        this.test = test;
        this.inputType = inputType;
        this.methodName = methodName;
        this.startCode = startCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

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

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }
}
