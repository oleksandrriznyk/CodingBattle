package com.codingbattle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "sessions")
public class Session {

    @Id
    private UUID id;
    private User playerFirst;
    private User playerSecond;
    private Task task;
    private SessionResult sessionResult;

    public   Session() {
    }

    public Session(UUID id, User playerFirst, User playerSecond, Task task) {
        this.id = id;
        this.playerFirst = playerFirst;
        this.playerSecond = playerSecond;
        this.task = task;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getPlayerFirst() {
        return playerFirst;
    }

    public void setPlayerFirst(User playerFirst) {
        this.playerFirst = playerFirst;
    }

    public User getPlayerSecond() {
        return playerSecond;
    }

    public void setPlayerSecond(User playerSecond) {
        this.playerSecond = playerSecond;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SessionResult getSessionResult() {
        return sessionResult;
    }

    public void setSessionResult(SessionResult sessionResult) {
        this.sessionResult = sessionResult;
    }
}
