package com.example.han.compass.request;

/**
 * Created by sjnam on 2016. 10. 3..
 */

public class UserProfilePosition {
    private int minX;           // 최소 x 좌표
    private int maxX;           // 최대 x 좌표
    private int minY;           // 최소 y 좌표
    private int maxY;           // 최대 y 좌표
    private String userId;      // 유저 id
    private String userName;    // 유저 이름

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
