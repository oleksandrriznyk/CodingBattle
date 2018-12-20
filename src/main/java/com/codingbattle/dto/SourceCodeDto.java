package com.codingbattle.dto;

public class SourceCodeDto {

    private String source;
    private String gameName;
    private String taskId;
    private String sessionId;

    public SourceCodeDto(String source, String gameName) {
        this.source = source;
        this.gameName = gameName;
    }

    public SourceCodeDto() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTaskId(){
        return taskId;
    }

    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "SourceCodeDto{" +
                "source='" + source + '\'' +
                ", gameName='" + gameName + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
