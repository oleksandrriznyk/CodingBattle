package com.codingbattle.service;

import com.codingbattle.entity.Test;

import java.util.List;

public interface TestService {

    Test findById(String testId);

    Test save(Test test);

    List<Test> findAll();

    void deleteById(String testId);
}
