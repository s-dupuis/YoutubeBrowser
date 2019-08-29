package com.kixot.youtubebrowser;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;
import com.kixot.youtubebrowser.utils.Permissions;

public class FabManager {

    private MainActivity activity;
    private UrlManager urlManager;
    private YoutubeManager youtubeManager;
    private FloatingActionButton downloadFab, downloadMusicFab, downloadVideoFab;
    private boolean isDownloadFabOpen = false;
    private DownloadsTable downloadsTable;

    public FabManager (MainActivity activity, UrlManager urlManager) {
        this.activity = activity;
        this.urlManager = urlManager;
        this.youtubeManager = new YoutubeManager(urlManager);
        this.downloadsTable = new DownloadsTable(activity);
        downloadsTable.open();
    }

    public void loadDownloadFabs() {
        downloadFab = (FloatingActionButton) activity.findViewById(R.id.downloadFab);
        downloadMusicFab = (FloatingActionButton) activity.findViewById(R.id.downloadMusicFab);
        downloadVideoFab = (FloatingActionButton) activity.findViewById(R.id.downloadVideoFab);

        downloadFab.setOnClickListener(view -> {
            if (isDownloadFabOpen) closeFABMenu();
            else showFABMenu();
        });

        downloadMusicFab.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            View dialogView = activity.getLayoutInflater().inflate(R.layout.alertdialog_download, null);
            alertDialog.setView(dialogView);
            alertDialog.setCancelable(false);

            ImageView thumbnailImageView = (ImageView) dialogView.findViewById(R.id.thumbnailImageView);
            EditText titleEditText = (EditText) dialogView.findViewById(R.id.titleEditText);
            EditText endTimeEditText = (EditText) dialogView.findViewById(R.id.endTimeEditText);

            alertDialog.setPositiveButton(R.string.download, (dialog, which) -> {
                long downloadId = downloadsTable.insertDownload(new Download(
                        titleEditText.getText().toString(),
                        0,
                        "audio"
                ));

                if (Permissions.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    youtubeManager.downloadAudio(downloadId, downloadsTable, activity.findViewById(android.R.id.content));
            });

            alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> {});

            AlertDialog createdAlertDialog = alertDialog.create();
            createdAlertDialog.show();

            createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            titleEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(titleEditText.getText().toString().length() > 0);
                }
            });

            youtubeManager.downloadThumbnail(thumbnailImageView);
            youtubeManager.getVideoDetails(titleEditText, endTimeEditText);

            createdAlertDialog.show();
        });

        downloadVideoFab.setOnClickListener(view -> Snackbar.make(view, "MP4", Snackbar.LENGTH_LONG).setAction("OK", null).show());
    }

    private void showFABMenu(){
        isDownloadFabOpen = true;
        downloadVideoFab.animate().translationY(-activity.getResources().getDimension(R.dimen.first_fab));
        downloadMusicFab.animate().translationY(-activity.getResources().getDimension(R.dimen.second_fab));
    }

    private void closeFABMenu(){
        isDownloadFabOpen = false;
        downloadMusicFab.animate().translationY(0);
        downloadVideoFab.animate().translationY(0);
    }

    public void showFab() {
        if (!urlManager.isVideo()) {
            downloadFab.hide();
            downloadMusicFab.hide();
            downloadVideoFab.hide();
        }
        else {
            downloadFab.show();
            downloadMusicFab.show();
            downloadVideoFab.show();
        }
    }

}
