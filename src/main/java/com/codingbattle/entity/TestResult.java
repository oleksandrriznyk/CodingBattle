package com.codingbattle.entity;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class TestResult extends Test {

    private String actualResults;
    private Boolean isPassed = false;

    public TestResult(String id, String inputParams, String outputParams) {
        super(id, inputParams, outputParams);
    }

    public TestResult(Test test){
        this.setId(test.getId());
        this.setInputParams(test.getInputParams());
        this.setOutputParams(test.getOutputParams());
    }

    public String getActualResults() {
        return actualResults;
    }

    public void setActualResults(String actualResults) {
        this.actualResults = actualResults;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }
}
