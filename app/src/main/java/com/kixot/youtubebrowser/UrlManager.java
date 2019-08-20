package com.kixot.youtubebrowser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

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
        String thumbnailUrl = YoutubeManager.thumbnailUrl.replace("[videoId]", videoId);
        Log.d("thumbnailUrl", videoId + "  " + thumbnailUrl);
        return thumbnailUrl;
    }

}
