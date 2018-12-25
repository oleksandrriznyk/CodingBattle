package com.codingbattle.dto;

import com.codingbattle.entity.TestResult;

import java.util.List;


public class TestResultDto {

    private List<TestResult> testResultList;
    private String status;
    private Long executionTime;

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

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }
}
