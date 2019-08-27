package com.kixot.youtubebrowser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.models.Download;
import com.kixot.youtubebrowser.utils.Format;

import java.util.ArrayList;

public class DownloadListViewAdapter extends BaseAdapter {

    private ArrayList<Download> downloads;
    private final LayoutInflater inflat;

    public DownloadListViewAdapter(Context c, ArrayList<Download> downloads) {
        this.inflat = LayoutInflater.from(c);
        this.downloads = downloads;
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

        viewHolder.titleDownloadTextView.setText(Format.formatLongTitle(download.getTitle(), convertView.getResources().getInteger(R.integer.title_max_length)));
        viewHolder.downloadProgressTextView.setText(download.getProgress() + "%");
        viewHolder.downloadProgressBar.setProgress(download.getProgress());
        viewHolder.downloadImageView.setImageResource(download.getType().equals("audio") ? R.drawable.baseline_music_note_black_24 : R.drawable.baseline_movie_black_24);

        convertView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), download.getTitle(), Toast.LENGTH_SHORT).show();
        });

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
