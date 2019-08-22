package com.kixot.youtubebrowser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.kixot.youtubebrowser.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class YoutubeManager {

    public static final String url = "https://m.youtube.com";
    public static final String thumbnailUrl = "https://img.youtube.com/vi/[videoId]/hqdefault.jpg";
    public static final String videoDatasUrl = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=[videoId]&format=json";

    private UrlManager urlManager;

    public YoutubeManager(UrlManager urlManager) {
        this.urlManager = urlManager;
    }

    public void downloadThumbnail (ImageView thumbnailImageView) {
        DownloadImageTask task = new DownloadImageTask(thumbnailImageView);
        task.execute(urlManager.getThumbnailUrl());
    }

    public void getVideoDatas (EditText titleEditText) {
        GetVideoDatasTask task = new GetVideoDatasTask(titleEditText);
        task.execute(urlManager.replaceVideoId(videoDatasUrl));

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private class GetVideoDatasTask extends AsyncTask<String, Void, String> {
        EditText titleEditText;

        public GetVideoDatasTask(EditText titleEditText) {
            this.titleEditText = titleEditText;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                String url = urls[0];
                HttpRequest httpRequest = new HttpRequest();

                JSONObject response = httpRequest.get(url);

                String title = response.getString("title");

                return title;

            } catch (JSONException e) {
                Log.e("JSONException", e.toString());
                e.printStackTrace();
                return "";
            }
        }

        protected void onPostExecute(String title) {
            titleEditText.setText(title);
        }

    }

}
