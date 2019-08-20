package com.kixot.youtubebrowser;

import android.util.Log;

public class UrlManager {

    private String initialUrl;
    private String currentUrl;

    UrlManager(String url) {
        this.initialUrl = url;
        this.setCurrentUrl(url);
    }

    public boolean isVideo() { return currentUrl.contains("watch?v="); }
    public boolean isYoutube() { return currentUrl.contains(initialUrl); }
    public boolean isYoutube(String url) { return url.contains(initialUrl); }

    String getCurrentUrl() {
        return currentUrl;
    }

    void setCurrentUrl(String currentUrl) {
        Log.d("setCurrentUrl", currentUrl);
        this.currentUrl = currentUrl;
    }
}
