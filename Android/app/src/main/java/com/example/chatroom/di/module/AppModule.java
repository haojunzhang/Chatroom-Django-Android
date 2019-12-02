package com.example.chatroom.di.module;

import android.content.Context;

import com.example.chatroom.base.ChatRoomApplication;


import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext() {
        return ChatRoomApplication.getContext();
    }

}
