package com.codingbattle.dto;

import com.codingbattle.entity.TestResult;
import lombok.Data;

import java.util.List;

@Data
public class TestResultDto {

    private List<TestResult> testResultList;
    private String status;
    private double executionTime;
}
