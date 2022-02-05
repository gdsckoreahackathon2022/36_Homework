package com.gdsc.homework.model;

public class ToDo {
    String roles;
    int imageResource;

    public ToDo(String roles, int imageResource) {
        this.roles = roles;
        this.imageResource = imageResource;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
