package com.example.chatroom.utils;

public class NativeUtils {

    public static final int SERVER_SIGN_PUB = 103;
    public static final int CHAT_ROOM_WS_URL = 201;

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getAppKey(int i);
}
