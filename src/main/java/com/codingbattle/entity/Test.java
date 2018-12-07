package com.codingbattle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "tests")
@Data
@AllArgsConstructor
public class Test {

    @Id
    private UUID id;
    private List<String> inputParams;
    private List<String> outputParams;
}
