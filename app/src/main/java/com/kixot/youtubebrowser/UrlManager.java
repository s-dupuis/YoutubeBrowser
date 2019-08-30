package com.kixot.youtubebrowser;

import android.util.Log;

public class UrlManager {

    private String initialUrl;
    private String currentUrl;

    private String[] connexionUrls = new String[]{
            "accounts.google.com",
            "accounts.youtube.com",
            "accounts.google.fr",
            "youtube.com"
    };

    UrlManager(String url) {
        this.initialUrl = url;
        this.setCurrentUrl(url);
    }

    public boolean isVideo() {
        return currentUrl.contains("watch?v=");
    }

    public boolean isYoutube() {
        return currentUrl.contains(initialUrl);
    }

    public boolean isYoutube(String url) {
        boolean allowed = false;
        for (String u : connexionUrls) allowed = allowed || url.contains(u);

        return url.contains(initialUrl) || allowed;
    }

    String getInitialUrl() {
        return initialUrl;
    }

    String getCurrentUrl() {
        return currentUrl;
    }

    void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getVideoId () {
        String[] tmp = currentUrl.split("=");
        return tmp[1];
    }

    public String getThumbnailUrl () {
        String videoId = getVideoId();
        return YoutubeManager.thumbnailUrl.replace("[videoId]", videoId);
    }

    public String replaceVideoId (String url) {
        return url.replace("[videoId]", getVideoId());
    }

}
