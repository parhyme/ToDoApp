package com.sample.todoapp.models;

public class User {
    private String username;
    private String password;
    private int id;

    public User(String username, String password, int userid) {
        this.username = username;
        this.password = password;
        this.id = userid;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
        return id;
    }

    public void setUserid(int userid) {
        this.id = userid;
    }
}
