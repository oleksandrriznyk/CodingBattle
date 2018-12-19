package com.codingbattle.dto;

import com.codingbattle.entity.TestResult;

import java.util.List;


public class TestResultDto {

    private List<TestResult> testResultList;
    private String status;
    private double executionTime;

    public List<TestResult> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<TestResult> testResultList) {
        this.testResultList = testResultList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }
}
