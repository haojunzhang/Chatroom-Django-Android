package com.example.chatroom.di.module;

import com.example.chatroom.ui.login.LoginActivity;
import com.example.chatroom.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity contributeLoginActivity();

}
