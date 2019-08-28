package com.kixot.youtubebrowser.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.model.quality.AudioQuality;
import com.kixot.youtubebrowser.MainActivity;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;

import java.io.File;
import java.util.List;

public class DownloadAudioTask extends AsyncTask<String, Void, Void> {

    private long downloadId;
    private DownloadsTable downloadsTable;

    public DownloadAudioTask (long downloadId, DownloadsTable downloadsTable) {
        this.downloadId = downloadId;
        this.downloadsTable = downloadsTable;
    }

    @Override
    protected Void doInBackground(String... videoIds) {

        try {
            YoutubeVideo video = YoutubeDownloader.getVideo(videoIds[0]);
            File outputDir = new File(MainActivity.downloadPath+"/musics");

            List<AudioFormat> audios = video.findAudioWithQuality(AudioQuality.high);
            AudioQuality[] audioQualities = new AudioQuality[] { AudioQuality.high, AudioQuality.medium, AudioQuality.low, AudioQuality.unknown };

            int i = 0;
            while (audios.size() == 0 && audioQualities.length != i) {
                audios = video.findAudioWithQuality(audioQualities[i++]);
                Log.d("DownloadAudio - length", audios.size()+"");
            }

            if (audios.size() > 0) {
                video.downloadAsync(audios.get(0), outputDir, new YoutubeDownloader.DownloadCallback() {
                    @Override
                    public void onDownloading(int progress) {
                        downloadsTable.updateProgress(downloadId, progress);
                        Log.d("DownloadAudio - onDl", progress+""); //TODO
                    }

                    @Override
                    public void onFinished(File file) {
                        Log.d("DownloadAud - onFinish", file.getAbsolutePath()); //TODO
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("DownloadAudio - onErr", throwable.getMessage()); //TODO
                    }
                });
            }

        } catch (Exception e) {
            Log.d("DownloadVid - catch", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
