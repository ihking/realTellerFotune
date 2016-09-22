package com.example.han.compass.utils;

/**
 * Created by han on 16. 8. 21..
 */

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-08-20.
 */
public class Repo_User {
    String _id;
    String name;
    Repo_Home home;
    String profile;
    String accessToken;
    ArrayList<String> commend_list;
    ArrayList<String> room_list;
    ArrayList<String> friend_list;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repo_Home getHome() {
        return home;
    }

    public void setHome(Repo_Home home) {
        this.home = home;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ArrayList<String> getCommend_list() {
        return commend_list;
    }

    public void setCommend_list(ArrayList<String> commend_list) {
        this.commend_list = commend_list;
    }

    public ArrayList<String> getRoom_list() {
        return room_list;
    }

    public void setRoom_list(ArrayList<String> room_list) {
        this.room_list = room_list;
    }

    public ArrayList<String> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(ArrayList<String> friend_list) {
        this.friend_list = friend_list;
    }
}