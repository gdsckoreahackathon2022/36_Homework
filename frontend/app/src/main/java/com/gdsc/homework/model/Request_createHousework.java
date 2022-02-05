package com.gdsc.homework.model;

import java.util.List;

public class Request_createHousework {

    private String token;
    private String name;
    private List<Long> userId;
    private List<String> day;
    private String startTime;
    private String finishTime;
    private boolean repeat;
    private int penalty;
    private String memo;

    public Request_createHousework() {
    }

    public Request_createHousework(String token, String name, List<Long> userId, List<String> day, String startTime, String finishTime, boolean repeat, int penalty, String memo) {
        this.token = token;
        this.name = name;
        this.userId = userId;
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.repeat = repeat;
        this.penalty = penalty;
        this.memo = memo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUserId() {
        return userId;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
