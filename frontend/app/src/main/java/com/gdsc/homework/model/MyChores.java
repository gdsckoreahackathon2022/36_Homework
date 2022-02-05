package com.gdsc.homework.model;

public class MyChores {
    String role;
    String memo;
    String time;

    public MyChores() {
    }

    public MyChores(String role, String memo, String time) {
        this.role = role;
        this.memo = memo;
        this.time = time;
    }

    public String getRole() {
        return role;
    }

    public String getMemo() {
        return memo;
    }

    public String getTime() {
        return time;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
