package com.kixot.youtubebrowser;

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
        this.fabManager.showFab();
        super.doUpdateVisitedHistory(view, url, isReload);
    }
}
