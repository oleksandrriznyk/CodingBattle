package com.codingbattle.entity;

public class SessionResult {

    private String firstPlayerLogin;
    private String secondPlayerLogin;
    private double firstPlayerExecutionTime;
    private double secondPlayerExecutionTime;
    private String winnerLogin;

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

    public double getFirstPlayerExecutionTime() {
        return firstPlayerExecutionTime;
    }

    public void setFirstPlayerExecutionTime(double firstPlayerExecutionTime) {
        this.firstPlayerExecutionTime = firstPlayerExecutionTime;
    }

    public double getSecondPlayerExecutionTime() {
        return secondPlayerExecutionTime;
    }

    public void setSecondPlayerExecutionTime(double secondPlayerExecutionTime) {
        this.secondPlayerExecutionTime = secondPlayerExecutionTime;
    }

    public String getWinnerLogin() {
        return winnerLogin;
    }

    public void setWinnerLogin(String winnerLogin) {
        this.winnerLogin = winnerLogin;
    }
}
