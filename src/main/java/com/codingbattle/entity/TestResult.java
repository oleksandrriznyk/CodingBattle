package com.codingbattle.entity;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class TestResult extends Test {

    private List<String> actualResults;
    private Boolean isPassed = false;

    public TestResult(UUID id, List<String> inputParams, List<String> outputParams) {
        super(id, inputParams, outputParams);
    }

    public TestResult(Test test){
        this.setInputParams(test.getInputParams());
        this.setOutputParams(test.getOutputParams());
    }

    public List<String> getActualResults() {
        return actualResults;
    }

    public void setActualResults(List<String> actualResults) {
        this.actualResults = actualResults;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }
}
