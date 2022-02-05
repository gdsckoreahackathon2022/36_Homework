package com.gdsc.homework.model;

public class FamilyChores {
    int imageResource;
    String role;
    String memo;

    public FamilyChores(int imageResource, String role, String memo) {
        this.imageResource = imageResource;
        this.role = role;
        this.memo = memo;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getRole() {
        return role;
    }

    public String getMemo() {
        return memo;
    }
}
