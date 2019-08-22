package com.kixot.youtubebrowser.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.kixot.youtubebrowser.utils.Format;

public class GetVideoDetailsTask extends AsyncTask<String, Void, VideoDetails> {
    EditText titleEditText;
    EditText endTimeEditText;

    public GetVideoDetailsTask(EditText titleEditText, EditText endTimeEditText) {
        this.titleEditText = titleEditText;
        this.endTimeEditText = endTimeEditText;
    }

    @Override
    protected VideoDetails doInBackground(String... videoIds) {
        try {
            YoutubeVideo video = YoutubeDownloader.getVideo(videoIds[0]);
            return video.details();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(VideoDetails details) {
        titleEditText.setText(details.title());
        endTimeEditText.setText(Format.formatTime(details.lengthSeconds()));
    }

}