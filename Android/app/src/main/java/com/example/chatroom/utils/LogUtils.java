package com.example.chatroom.utils;

import android.util.Log;

import com.example.chatroom.BuildConfig;
import com.orhanobut.logger.Logger;

public class LogUtils {

    public static final String TAG = "@@@";

    public static void d(String key, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.d(key, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void json(String json) {
        if (BuildConfig.IS_DEBUG) {
            Logger.json(json);
        }
    }
}
