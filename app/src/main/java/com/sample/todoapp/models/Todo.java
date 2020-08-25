package com.sample.todoapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {
    private String text;
    private boolean isChecked;
    private int userId;
    private int id;
    
    public Todo(String text, boolean isChecked, int userid) {
        this.text = text;
        this.isChecked = isChecked;
        this.userId = userid;
    }

    public Todo(String text, int userid) {
        this.text = text;
        this.isChecked = isChecked;
        this.userId = userid;
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
        return userId;
    }

    public void setUserid(int userid) {
        this.userId = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.userId);
        dest.writeInt(this.id);
    }

    protected Todo(Parcel in) {
        this.text = in.readString();
        this.isChecked = in.readByte() != 0;
        this.userId = in.readInt();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}
