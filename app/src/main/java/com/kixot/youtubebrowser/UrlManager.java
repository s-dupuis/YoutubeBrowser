package com.kixot.youtubebrowser;

import android.util.Log;

public class UrlManager {

    private String currentUrl;

    UrlManager(String url) {
        this.setCurrentUrl(url);
    }

    public boolean isVideo() {
        return currentUrl.contains("watch?v=");
    }

    String getCurrentUrl() {
        return currentUrl;
    }

    void setCurrentUrl(String currentUrl) {
        Log.d("setCurrentUrl", currentUrl);
        this.currentUrl = currentUrl;
    }
}
