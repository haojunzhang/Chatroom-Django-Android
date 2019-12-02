package com.example.chatroom.base;

import android.app.Activity;

import com.example.chatroom.data.repository.app.AppRepository;
import com.example.chatroom.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ChatRoomApplication extends BaseApplication implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    AppRepository mAppRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        // DI
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
