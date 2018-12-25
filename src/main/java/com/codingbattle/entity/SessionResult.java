package com.codingbattle.entity;

public class SessionResult {

    private String firstPlayerLogin;
    private String secondPlayerLogin;
    private Long firstPlayerExecutionTime;
    private Long secondPlayerExecutionTime;
    private String winnerLogin;

    public SessionResult(String firstPlayerLogin, String secondPlayerLogin) {
        this.firstPlayerLogin = firstPlayerLogin;
        this.secondPlayerLogin = secondPlayerLogin;
    }

    public String getFirstPlayerLogin() {
        return firstPlayerLogin;
    }

    public void setFirstPlayerLogin(String firstPlayerLogin) {
        this.firstPlayerLogin = firstPlayerLogin;
    }

    public String getSecondPlayerLogin() {
        return secondPlayerLogin;
    }

    public void setSecondPlayerLogin(String secondPlayerLogin) {
        this.secondPlayerLogin = secondPlayerLogin;
    }

    public Long getFirstPlayerExecutionTime() {
        return firstPlayerExecutionTime;
    }

    public void setFirstPlayerExecutionTime(Long firstPlayerExecutionTime) {
        this.firstPlayerExecutionTime = firstPlayerExecutionTime;
    }

    public Long getSecondPlayerExecutionTime() {
        return secondPlayerExecutionTime;
    }

    public void setSecondPlayerExecutionTime(Long secondPlayerExecutionTime) {
        this.secondPlayerExecutionTime = secondPlayerExecutionTime;
    }

    public String getWinnerLogin() {
        return winnerLogin;
    }

    public void setWinnerLogin(String winnerLogin) {
        this.winnerLogin = winnerLogin;
    }
}
