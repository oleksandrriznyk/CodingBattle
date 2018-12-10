package com.codingbattle.controller;

import com.codingbattle.entity.Test;
import com.codingbattle.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/{testId}")
    public Test getById(@PathVariable("testId") String testId){
        return testService.findById(testId);
    }

    @PostMapping("/save")
    public Test save(@RequestBody Test test){
        return testService.save(test);
    }

    @GetMapping("/all")
    public List<Test> findAll(){
        return testService.findAll();
    }

    @GetMapping("/delete/{testId}")
    public void delete(@PathVariable("testId") String testId){
        testService.deleteById(testId);
    }

}
