package com.codingbattle.controller;

import com.codingbattle.entity.Test;
import com.codingbattle.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
