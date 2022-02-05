package com.gdsc.homework.model;

public class Request_getDeposit {

    private String token;
    private long roomId;

    public Request_getDeposit() { }

    public Request_getDeposit(String token, long roomId) {
        this.token = token;
        this.roomId = roomId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
