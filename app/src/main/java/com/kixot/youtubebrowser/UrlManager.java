package com.kixot.youtubebrowser;

import android.util.Log;

public class UrlManager {

    private String currentUrl;

    public UrlManager(String url) {
        this.setCurrentUrl(url);
    }

    public boolean isVideo() {
        return currentUrl.contains("watch?v=");
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        Log.d("setCurrentUrl", currentUrl);
        this.currentUrl = currentUrl;
    }
}
