package com.gdsc.homework.model;

public class Response_checkRoom {

    private Integer status;
    private String message;
    private data data;

    public Response_checkRoom() { }

    public Response_checkRoom(Integer status, String message, data data) {
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

    public Response_checkRoom.data getData() {
        return data;
    }

    public void setData(Response_checkRoom.data data) {
        this.data = data;
    }

    public class data {
        private boolean result;
        private long roomId;

        public data() { }

        public data(boolean result, Integer roomId) {
            this.result = result;
            this.roomId = roomId;
        }

        public boolean isResult() { return result; }

        public void setResult(boolean result) { this.result = result; }

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }
    }
}
