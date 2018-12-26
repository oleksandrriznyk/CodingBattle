package com.codingbattle.dto;

public class SessionResultDto {

    private String playerFirstLogin;
    private String playerSecondLogin;
    private String isWinner;

    public String getPlayerFirstLogin() {
        return playerFirstLogin;
    }

    public void setPlayerFirstLogin(String playerFirstLogin) {
        this.playerFirstLogin = playerFirstLogin;
    }

    public String getPlayerSecondLogin() {
        return playerSecondLogin;
    }

    public void setPlayerSecondLogin(String playerSecondLogin) {
        this.playerSecondLogin = playerSecondLogin;
    }

    public String getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(String isWinner) {
        this.isWinner = isWinner;
    }
}
