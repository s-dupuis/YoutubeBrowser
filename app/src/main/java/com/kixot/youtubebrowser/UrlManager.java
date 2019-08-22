package com.kixot.youtubebrowser;

public class UrlManager {

    private String initialUrl;
    private String currentUrl;

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
        return url.contains(initialUrl);
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
