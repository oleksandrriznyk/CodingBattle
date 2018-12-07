package com.codingbattle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
public class Task {

    @Id
    private UUID id;
    private String taskText;
    private Test test;

}
