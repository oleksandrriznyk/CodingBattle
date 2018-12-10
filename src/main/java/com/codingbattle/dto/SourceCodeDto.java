package com.codingbattle.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SourceCodeDto {

    private String source;
    private String gameName;
    private String taskId;
}
