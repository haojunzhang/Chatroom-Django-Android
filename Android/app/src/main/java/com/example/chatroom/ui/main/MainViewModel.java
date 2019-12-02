package com.example.chatroom.ui.main;

import androidx.lifecycle.MutableLiveData;

import com.example.chatroom.base.BaseViewModel;
import com.example.chatroom.data.entity.ChatMessage;
import com.example.chatroom.data.repository.app.AppRepository;
import com.example.chatroom.data.websocket.DefaultWebSocketCallback;
import com.example.chatroom.data.websocket.WebSocketManger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    // repository
    private final WebSocketManger mWebSocketManger;
    private final AppRepository mAppRepository;

    // live data
    private final MutableLiveData<List<ChatMessage>> mChatMessageList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsClose = new MutableLiveData<>();

    private final WebSocketManger.Callback mWebSocketCallback = new DefaultWebSocketCallback() {

        @Override
        public void onMessage(ChatMessage chatMessage) {
            addChatMessage(chatMessage);
        }

        private void addChatMessage(ChatMessage chatMessage) {
            List<ChatMessage> chatMessageList = mChatMessageList.getValue();
            if (chatMessageList == null) {
                chatMessageList = new ArrayList<>();
            }
            chatMessageList.add(chatMessage);
            mChatMessageList.postValue(chatMessageList);
        }
    };

    @Inject
    public MainViewModel(WebSocketManger mWebSocketManger,AppRepository mAppRepository) {
        this.mWebSocketManger = mWebSocketManger;
        this.mAppRepository = mAppRepository;
    }

    public MutableLiveData<List<ChatMessage>> chatMessageList() {
        return mChatMessageList;
    }

    public MutableLiveData<Boolean> isClose() {
        return mIsClose;
    }

    public void onResume() {
        mWebSocketManger.connect();
        mWebSocketManger.setCallback(mWebSocketCallback);
    }

    public void onPause() {
        mWebSocketManger.disconnect();
    }

    public void sendMessage(String message) {
        mWebSocketManger.send(
                mAppRepository.getUserName(),
                message
        );
    }
}
