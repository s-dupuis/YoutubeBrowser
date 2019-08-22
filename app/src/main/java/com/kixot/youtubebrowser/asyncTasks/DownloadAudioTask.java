package com.kixot.youtubebrowser.asyncTasks;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.model.quality.AudioQuality;
import com.kixot.youtubebrowser.MainActivity;

import java.io.File;
import java.util.List;

public class DownloadAudioTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... videoIds) {

        try {
            YoutubeVideo video = YoutubeDownloader.getVideo(videoIds[0]);
            File outputDir = new File(MainActivity.downloadPath+"/musics");

            List<AudioFormat> audios = video.findAudioWithQuality(AudioQuality.high);
            AudioQuality[] audioQualities = new AudioQuality[] { AudioQuality.high, AudioQuality.medium, AudioQuality.low };

            int i = 0;
            while (audios.size() == 0 && audioQualities.length != i) {
                audios = video.findAudioWithQuality(audioQualities[i++]);
                Log.d("DownloadVid - length", audios.size()+"");
            }

            if (audios.size() > 0) {
                video.downloadAsync(audios.get(0), outputDir, new YoutubeDownloader.DownloadCallback() {
                    @Override
                    public void onDownloading(int progress) {
                        Log.d("DownloadVid - onDl", progress+"");
                    }

                    @Override
                    public void onFinished(File file) {
                        Log.d("DownloadVid - onFinish", file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("DownloadVid - onErr", throwable.getMessage());
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
