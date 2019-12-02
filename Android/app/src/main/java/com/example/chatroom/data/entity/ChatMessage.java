package com.example.chatroom.data.entity;

public class ChatMessage {
    private String user_name;
    private String message;

    // android
    private int userNameColor;

    public int getUserNameColor() {
        return userNameColor;
    }

    public void setUserNameColor(int userNameColor) {
        this.userNameColor = userNameColor;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
