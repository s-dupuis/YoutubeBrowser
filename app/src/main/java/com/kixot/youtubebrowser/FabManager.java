package com.kixot.youtubebrowser;

import android.Manifest;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
            View dialogView = activity.getLayoutInflater().inflate(R.layout.download_alertdialog, null);
            alertDialog.setView(dialogView);
            alertDialog.setCancelable(false);

            ImageView thumbnailImageView = (ImageView) dialogView.findViewById(R.id.thumbnailImageView);
            EditText titleEditText = (EditText) dialogView.findViewById(R.id.titleEditText);
            EditText endTimeEditText = (EditText) dialogView.findViewById(R.id.endTimeEditText);

            youtubeManager.downloadThumbnail(thumbnailImageView);
            youtubeManager.getVideoDetails(titleEditText, endTimeEditText);

            long downloadId = downloadsTable.insertDownload(new Download(
                    titleEditText.getText().toString(),
                    0,
                    "audio"
            ));

            alertDialog.setPositiveButton(R.string.download, (dialog, which) -> {
                if (Permissions.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    youtubeManager.downloadAudio(downloadId, downloadsTable);
            });

            alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> {});
            alertDialog.create().show();
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
