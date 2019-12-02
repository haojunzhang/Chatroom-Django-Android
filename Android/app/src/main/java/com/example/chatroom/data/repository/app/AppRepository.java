package com.example.chatroom.data.repository.app;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepository implements AppDataSource {

    private String userName;

    @Inject
    public AppRepository() {
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
