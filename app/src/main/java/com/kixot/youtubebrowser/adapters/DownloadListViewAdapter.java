package com.kixot.youtubebrowser.adapters;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;
import com.kixot.youtubebrowser.utils.Format;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadListViewAdapter extends BaseAdapter {

    private ArrayList<Download> downloads;
    private final LayoutInflater inflat;
    private DownloadsTable downloadsTable;

    public DownloadListViewAdapter(Activity activity, ArrayList<Download> downloads, DownloadsTable downloadsTable) {
        this.inflat = LayoutInflater.from(activity);
        this.downloads = downloads;
        this.downloadsTable = downloadsTable;
    }


    @Override
    public int getCount() {
        return downloads.size();
    }

    @Override
    public Object getItem(int position) {
        return downloads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return downloads.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflat.inflate(R.layout.row_download, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.downloadProgressTextView = (TextView) convertView.findViewById(R.id.downloadProgressTextView);
            viewHolder.titleDownloadTextView = (TextView) convertView.findViewById(R.id.titleDownloadTextView);
            viewHolder.downloadImageView = (ImageView) convertView.findViewById(R.id.downloadImageView);
            viewHolder.downloadProgressBar = (ProgressBar) convertView.findViewById(R.id.downloadProgressBar);
            //viewHolder.downloadProgressButton = (Button) convertView.findViewById(R.id.downloadProgressButton);

            convertView.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder)convertView.getTag();

        Download download = downloads.get(position);

        int color = Color.BLUE;
        String progressText = "0%";

        switch (download.getStatus()) {
            case "downloading":
                progressText = download.getProgress() + "%";
                color = Color.rgb(51, 153, 204);
                break;
            case "error":
                progressText = "Une erreur est survenue.";
                color = Color.rgb(200, 0, 0);
                break;
            case "canceled":
                progressText = "Téléchargement annulé.";
                color = Color.GRAY;
                break;
            case "finished":
                progressText = "Téléchargement terminé.";
                color = Color.rgb(0, 204, 0);
                break;
        }

        viewHolder.titleDownloadTextView.setText(Format.formatLongTitle(download.getTitle(), convertView.getResources().getInteger(R.integer.title_max_length)));
        viewHolder.downloadProgressBar.setProgress(download.getProgress());
        viewHolder.downloadImageView.setImageResource(download.getType().equals("audio") ? R.drawable.baseline_music_note_black_24 : R.drawable.baseline_movie_black_24);
        viewHolder.downloadProgressTextView.setText(progressText);
        viewHolder.downloadProgressBar.setProgressTintList(ColorStateList.valueOf(color));

        convertView.setOnClickListener(v -> Toast.makeText(v.getContext(), download.getTitle(), Toast.LENGTH_SHORT).show());

        return convertView;
    }

    static class ViewHolder{
        TextView titleDownloadTextView;
        TextView downloadProgressTextView;
        ProgressBar downloadProgressBar;
        ImageView downloadImageView;
        //Button downloadProgressButton;
    }

}
