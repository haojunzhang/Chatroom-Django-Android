package com.example.chatroom.di.component;

import com.example.chatroom.base.ChatRoomApplication;
import com.example.chatroom.di.module.ActivityModule;
import com.example.chatroom.di.module.AppModule;
import com.example.chatroom.di.module.FragmentModule;
import com.example.chatroom.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ViewModelModule.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ChatRoomApplication app);

        AppComponent build();
    }

    void inject(ChatRoomApplication app);
}
