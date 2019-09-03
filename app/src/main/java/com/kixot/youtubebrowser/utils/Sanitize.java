package com.kixot.youtubebrowser.utils;

import android.text.Editable;

public class Sanitize {

    private final static String[] forbiddenChars = new String[]{
            "\\",
            "/",
            ":",
            "*",
            "?",
            "\"",
            "<",
            ">",
            "|"
    };

    public static String sanitizeTitle (Editable title) {
        return sanitizeTitle(title.toString());
    }

    public static String sanitizeTitle (String title) {
        String sTitle = title;

        for (String c : forbiddenChars)
            sTitle = sTitle.replace(c, "");

        return sTitle;
    }

}
