package com.codingbattle.service.impl;

import com.codingbattle.entity.Test;
import com.codingbattle.repository.TestRepository;
import com.codingbattle.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Test findById(String testId) {
        return testRepository.findOne(testId);
    }

    @Override
    public Test save(Test test) {
        Test tempTest = testRepository.findOne(test.getId());
        if(tempTest!=null){
            tempTest.setInputParams(test.getInputParams());
            tempTest.setOutputParams(test.getOutputParams());
            return testRepository.save(tempTest);
        }
        return testRepository.save(test);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public void deleteById(String testId) {
        testRepository.delete(testId);
    }
}
