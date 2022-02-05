package com.gdsc.homework.model;

public class Request_participateRoom {

    private String token;
    private String roomCode;

    public Request_participateRoom() { }

    public Request_participateRoom(String token, String roomCode) {
        this.token = token;
        this.roomCode = roomCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}