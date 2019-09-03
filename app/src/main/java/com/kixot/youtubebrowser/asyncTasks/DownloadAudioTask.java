package com.kixot.youtubebrowser.asyncTasks;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.model.quality.AudioQuality;
import com.kixot.youtubebrowser.MainActivity;
import com.kixot.youtubebrowser.NotificationsManager;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;

import java.io.File;
import java.util.List;

public class DownloadAudioTask extends AsyncTask<String, Void, Void> {

    private Download download;
    private DownloadsTable downloadsTable;
    private View viewSnackbar;
    private String downloadsPath;

    public DownloadAudioTask (long downloadId, DownloadsTable downloadsTable, View viewSnackbar, String downloadsPath) {
        this.download = downloadsTable.getDownload(downloadId);
        this.downloadsTable = downloadsTable;
        this.viewSnackbar = viewSnackbar;
        this.downloadsPath = downloadsPath;
    }

    @Override
    protected Void doInBackground(String... videoIds) {

        long downloadId = download.getId();
        String title = download.getTitle();

        try {
            YoutubeVideo video = YoutubeDownloader.getVideo(videoIds[0]);
            File outputDir = new File(downloadsPath+"/musics");

            List<AudioFormat> audios = video.findAudioWithQuality(AudioQuality.high);
            AudioQuality[] audioQualities = new AudioQuality[] { AudioQuality.high, AudioQuality.medium, AudioQuality.low, AudioQuality.unknown };

            int i = 0;
            while (audios.size() == 0 && audioQualities.length != i) {
                audios = video.findAudioWithQuality(audioQualities[i++]);
            }

            if (audios.size() > 0) {
                video.downloadAsync(audios.get(0), outputDir, new YoutubeDownloader.DownloadCallback() {
                    @Override
                    public void onDownloading(int progress) {
                        downloadsTable.updateProgress(downloadId, progress);
                    }

                    @Override
                    public void onFinished(File file) {
                        File newFile = new File(file.getPath(), title);
                        file.renameTo(newFile);

                        downloadsTable.updateProgress(downloadId, 100);
                        downloadsTable.updateStatus(downloadId, "finished");
                        Snackbar.make(viewSnackbar, "Le téléchargement de "+title+" est terminé.", Snackbar.LENGTH_LONG).setAction("OK", v -> {}).show();
                        NotificationsManager.sendNotification(downloadsTable.getContext(), "Téléchargement terminé", title);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        downloadsTable.updateStatus(downloadId, "error");
                        Snackbar.make(viewSnackbar, "Le téléchargement de "+title+" a échoué.", Snackbar.LENGTH_LONG).setAction("OK", v -> {}).show();
                        NotificationsManager.sendNotification(downloadsTable.getContext(), "Erreur de téléchargement", title);
                        Log.d("DownloadAudio - onErr", throwable.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            downloadsTable.updateStatus(downloadId, "error");
            Snackbar.make(viewSnackbar, "Le téléchargement de "+title+" a échoué.", Snackbar.LENGTH_LONG).setAction("OK", v -> {}).show();
            NotificationsManager.sendNotification(downloadsTable.getContext(), "Erreur de téléchargement", title);
            Log.d("DownloadAudio - catch", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
