package com.sample.todoapp.models;

public class Todo {
    private String text;
    private boolean isChecked;
    private int userid;
    private int todoid;

    public Todo(String text, boolean isChecked, int userid) {
        this.text = text;
        this.isChecked = isChecked;
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
