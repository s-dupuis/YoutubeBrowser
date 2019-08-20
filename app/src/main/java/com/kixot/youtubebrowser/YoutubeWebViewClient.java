package com.kixot.youtubebrowser;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class YoutubeWebViewClient extends WebViewClient {

    private UrlManager urlManager;
    private FabManager fabManager;

    public YoutubeWebViewClient(UrlManager urlManager, FabManager fabManager) {
        super();
        this.fabManager = fabManager;
        this.urlManager = urlManager;
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        urlManager.setCurrentUrl(url);
        fabManager.showFab();

        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return !urlManager.isYoutube(request.getUrl().toString());
    }
}
