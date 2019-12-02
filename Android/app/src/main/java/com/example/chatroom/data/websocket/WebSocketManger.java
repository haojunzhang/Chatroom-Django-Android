package com.example.chatroom.data.websocket;

import com.example.chatroom.data.entity.ChatMessage;
import com.example.chatroom.data.entity.SendMessage;
import com.example.chatroom.utils.LogUtils;
import com.example.chatroom.utils.NativeUtils;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WebSocketManger {
    private static final String TAG = "WebSocketManger";
    public static final String WS_URL = "ws://192.168.0.179:8000/ws/chat/";

    @Inject
    public WebSocketManger() {
    }

    public interface Callback {
        void onMessage(ChatMessage chatMessage);
    }

    private WebSocketClient mWebSocketClient;
    private Callback callback;

    private void init() {
        try {
            mWebSocketClient = new WebSocketClient(new URI(WS_URL)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    LogUtils.d(TAG, "onOpen");
                    if (handshakedata != null) {
                        LogUtils.d(TAG, "status:" + handshakedata.getHttpStatus());
                        LogUtils.d(TAG, "message:" + handshakedata.getHttpStatusMessage());
                    }
                }

                @Override
                public void onMessage(String message) {
                    LogUtils.d(TAG, "onMessage:" + message);
                    ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
                    callback.onMessage(chatMessage);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    LogUtils.d(TAG, "onClose:" + code + " " + reason + " " + remote);
                }

                @Override
                public void onClosing(int code, String reason, boolean remote) {
                    super.onClosing(code, reason, remote);
                    LogUtils.d(TAG, "onClosing:" + code + " " + reason + " " + remote);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    LogUtils.d(TAG, "onError");
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d(TAG, "Exception");
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void connect() {
        if (mWebSocketClient == null) {
            init();
        }
        mWebSocketClient.connect();
    }

    public void disconnect() {
        mWebSocketClient.close();
    }

    public void send(String userName, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setUser_name(userName);
        sendMessage.setMessage(message);
        mWebSocketClient.send(new Gson().toJson(sendMessage));
        LogUtils.d(TAG, "sendMessage:" + new Gson().toJson(sendMessage));
    }
}
