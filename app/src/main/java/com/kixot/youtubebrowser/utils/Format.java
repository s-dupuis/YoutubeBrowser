package com.kixot.youtubebrowser.utils;

public class Format {

    private static String formatTimeUnit (int t) {
        String timeUnit = Integer.toString(t);
        return timeUnit.length() == 1 ? "0"+timeUnit : timeUnit;
    }

    public static String formatTime (int seconds) {
        return formatTimeUnit(seconds/3600) + ":" + formatTimeUnit((seconds%3600)/60) + ":" + formatTimeUnit(seconds%3600%60);
    }

    public static String formatLongTitle (String title, int maxLength) {
        if (title.length() <= maxLength) return title;
        else {
            String formattedTitle = title.substring(0, maxLength-1);
            return formattedTitle + "…";
        }
    }

}
