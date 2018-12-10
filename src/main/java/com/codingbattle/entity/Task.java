package com.codingbattle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
public class Task {

    @Id
    private String id;
    private String taskText;
    private List<Test> test;
    private String inputType;
    private String methodName;
    private String startCode;

}
