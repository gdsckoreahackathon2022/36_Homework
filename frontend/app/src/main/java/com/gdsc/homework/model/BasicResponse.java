package com.gdsc.homework.model;

public class BasicResponse {

    private Integer status;
    private String message;
    private String data;

    public BasicResponse() { }

    public BasicResponse(Integer status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}
