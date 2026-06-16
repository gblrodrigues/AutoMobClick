package com.gblrod.automobclick.util;

public final class TimeFormatter {
    public static String formatMillis(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;

        return minutes + "m " + seconds % 60 + "s";
    }
}