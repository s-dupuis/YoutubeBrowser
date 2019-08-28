package com.kixot.youtubebrowser;

import android.widget.EditText;
import android.widget.ImageView;

import com.kixot.youtubebrowser.asyncTasks.DownloadThumbnailTask;
import com.kixot.youtubebrowser.asyncTasks.DownloadAudioTask;
import com.kixot.youtubebrowser.asyncTasks.GetVideoDetailsTask;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;

public class YoutubeManager {

    public static final String url = "https://m.youtube.com/";
    public static final String thumbnailUrl = "https://img.youtube.com/vi/[videoId]/hqdefault.jpg";

    private UrlManager urlManager;

    public YoutubeManager(UrlManager urlManager) {
        this.urlManager = urlManager;
    }

    public void downloadThumbnail (ImageView thumbnailImageView) {
        DownloadThumbnailTask task = new DownloadThumbnailTask(thumbnailImageView);
        task.execute(urlManager.getThumbnailUrl());
    }

    public void getVideoDetails (EditText titleEditText, EditText endTimeEditText) {
        GetVideoDetailsTask task = new GetVideoDetailsTask(titleEditText, endTimeEditText);
        task.execute(urlManager.getVideoId());
    }

    public void downloadAudio (long downloadId, DownloadsTable downloadsTable) {
        DownloadAudioTask task = new DownloadAudioTask(downloadId, downloadsTable);
        task.execute(urlManager.getVideoId());
    }

}
