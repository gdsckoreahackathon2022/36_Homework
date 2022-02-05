package com.gdsc.homework.model;

public class Response_createRoom {

    private Integer status;
    private String message;
    private Response_createRoom.data data;

    public Response_createRoom() { }

    public Response_createRoom(Integer status, String message, Response_createRoom.data data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response_createRoom.data getData() {
        return data;
    }

    public void setData(Response_createRoom.data data) {
        this.data = data;
    }

    public class data {
        private String roomCode;
        private long roomId;

        public data() {
        }

        public data(String roomCode, long roomId) {
            this.roomCode = roomCode;
            this.roomId = roomId;
        }

        public String getRoomCode() {
            return roomCode;
        }

        public void setRoomCode(String roomCode) {
            this.roomCode = roomCode;
        }

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }
    }
}
