package com.example.chatroom.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    private static List<Integer> colorList;

    static {
        colorList = new ArrayList<>();
        colorList.add(android.R.color.holo_blue_light);
        colorList.add(android.R.color.holo_green_light);
        colorList.add(android.R.color.holo_red_light);
        colorList.add(android.R.color.holo_blue_dark);
        colorList.add(android.R.color.holo_green_dark);
        colorList.add(android.R.color.holo_red_dark);
        colorList.add(android.R.color.holo_purple);
        colorList.add(android.R.color.holo_orange_light);
        colorList.add(android.R.color.holo_orange_dark);
        colorList.add(android.R.color.holo_blue_bright);
    }

    public static int getColor(Context context, int position) {
        return ContextCompat.getColor(context, colorList.get(position % colorList.size()));
    }
}
