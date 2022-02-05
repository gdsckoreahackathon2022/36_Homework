package com.gdsc.homework.model;

public class Request_rouletteResult {

    private String token;
    private Long houseworkId;
    private Long userId;

    public Request_rouletteResult() {
    }

    public Request_rouletteResult(String token, Long houseworkId, Long userId) {
        this.token = token;
        this.houseworkId = houseworkId;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getHouseworkId() {
        return houseworkId;
    }

    public void setHouseworkId(Long houseworkId) {
        this.houseworkId = houseworkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
