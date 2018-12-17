package com.codingbattle.dto;

public class ImportsDto {

    private String taskId;
    private StringBuilder imports;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public StringBuilder getImports() {
        return imports;
    }

    public void setImports(StringBuilder imports) {
        this.imports = imports;
    }
}
