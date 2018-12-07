package com.codingbattle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "sessions")
@Data
@AllArgsConstructor
public class Session {

    @Id
    private UUID id;
    private User playerFirst;
    private User playerSecond;
    private Task task;

}
