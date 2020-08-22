package com.sample.todoapp.models;

public class User {
    private String username;
    private String password;
    private int userid;

    public User(String username, String password, int userid) {
        this.username = username;
        this.password = password;
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
