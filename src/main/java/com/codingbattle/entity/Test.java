package com.codingbattle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "tests")
public class Test {

    @Id
    private UUID id;
    private List<String> inputParams;
    private List<String> outputParams;

    public Test() {
    }

    public Test(UUID id, List<String> inputParams, List<String> outputParams) {
        this.id = id;
        this.inputParams = inputParams;
        this.outputParams = outputParams;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getInputParams() {
        return inputParams;
    }

    public void setInputParams(List<String> inputParams) {
        this.inputParams = inputParams;
    }

    public List<String> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(List<String> outputParams) {
        this.outputParams = outputParams;
    }
}
