package com.kixot.youtubebrowser;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class YoutubeWebViewClient extends WebViewClient {

    private UrlManager urlManager;

    public YoutubeWebViewClient(UrlManager urlManager) {
        super();
        this.urlManager = urlManager;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.urlManager.setCurrentUrl(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        this.urlManager.setCurrentUrl(url);
    }
}
