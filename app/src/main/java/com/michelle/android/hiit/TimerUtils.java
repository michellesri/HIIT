package com.michelle.android.hiit;

public class TimerUtils {

    public static String renderTimer(int time) {
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
