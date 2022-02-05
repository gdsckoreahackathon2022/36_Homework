package com.gdsc.homework.model;

public class Request_addDeposit {

    private String token;
    private int amount;

    public Request_addDeposit() { }

    public Request_addDeposit(String token, int amount) {
        this.token = token;
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
