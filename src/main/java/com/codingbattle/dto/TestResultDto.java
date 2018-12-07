package com.codingbattle.dto;

import com.codingbattle.entity.TestResult;
import lombok.Data;

import java.util.List;

@Data
public class TestResultDto {

    List<TestResult> testResultList;
    String status;
}
