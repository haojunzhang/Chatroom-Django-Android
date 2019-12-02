package com.example.chatroom.ui.login;

import androidx.lifecycle.MutableLiveData;

import com.example.chatroom.base.BaseViewModel;
import com.example.chatroom.data.repository.app.AppRepository;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    // repository
    private final AppRepository mAppRepository;

    // live data
    private final MutableLiveData<Boolean> mLoginSuccess = new MutableLiveData<>();

    @Inject
    public LoginViewModel(AppRepository mAppRepository) {
        this.mAppRepository = mAppRepository;
    }

    public MutableLiveData<Boolean> loginSuccess() {
        return mLoginSuccess;
    }

    public void login(String userName){
        if (userName.isEmpty()){
            return;
        }

        mAppRepository.setUserName(userName);
        mLoginSuccess.setValue(true);
    }

}
