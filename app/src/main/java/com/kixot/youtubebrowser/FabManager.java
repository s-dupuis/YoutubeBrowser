package com.kixot.youtubebrowser;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;
import com.kixot.youtubebrowser.utils.Permissions;
import com.kixot.youtubebrowser.utils.Sanitize;

public class FabManager {

    private MainActivity activity;
    private UrlManager urlManager;
    private YoutubeManager youtubeManager;
    private FloatingActionButton downloadFab, downloadMusicFab, downloadVideoFab;
    private boolean isDownloadFabOpen = false;
    private DownloadsTable downloadsTable;
    private boolean wifiOnly;
    private String downloadsPath;

    public FabManager (MainActivity activity, UrlManager urlManager) {
        this.activity = activity;
        this.urlManager = urlManager;
        this.youtubeManager = new YoutubeManager(urlManager);
        this.downloadsTable = new DownloadsTable(activity);
        this.wifiOnly = activity.preference_WifiOnly;
        this.downloadsPath = activity.preference_downloadsPath;

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

            if (wifiOnly && !activity.checkHasWifi()) {
                showWifiOnly();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                View dialogView = activity.getLayoutInflater().inflate(R.layout.alertdialog_download, null);
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(false);

                ImageView thumbnailImageView = (ImageView) dialogView.findViewById(R.id.thumbnailImageView);
                EditText titleEditText = (EditText) dialogView.findViewById(R.id.titleEditText);
                EditText endTimeEditText = (EditText) dialogView.findViewById(R.id.endTimeEditText);

                alertDialog.setPositiveButton(R.string.download, (dialog, which) -> {

                    long downloadId = downloadsTable.insertDownload(new Download(
                            Sanitize.sanitizeTitle(titleEditText.getText()),
                            0,
                            "audio"
                    ));

                    if (Permissions.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        youtubeManager.downloadAudio(downloadId, downloadsTable, activity.findViewById(android.R.id.content), downloadsPath);
                });

                alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> { });

                AlertDialog createdAlertDialog = alertDialog.create();
                createdAlertDialog.show();

                createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                titleEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(titleEditText.getText().toString().length() > 0);
                    }
                });

                youtubeManager.downloadThumbnail(thumbnailImageView);
                youtubeManager.getVideoDetails(titleEditText, endTimeEditText);

                createdAlertDialog.show();
            }

        });

        downloadVideoFab.setOnClickListener(view -> {
            if (wifiOnly && !activity.checkHasWifi()) {
                showWifiOnly();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                View dialogView = activity.getLayoutInflater().inflate(R.layout.alertdialog_download, null);
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(false);

                ImageView thumbnailImageView = (ImageView) dialogView.findViewById(R.id.thumbnailImageView);
                EditText titleEditText = (EditText) dialogView.findViewById(R.id.titleEditText);
                EditText endTimeEditText = (EditText) dialogView.findViewById(R.id.endTimeEditText);

                alertDialog.setPositiveButton(R.string.download, (dialog, which) -> {

                    long downloadId = downloadsTable.insertDownload(new Download(
                            Sanitize.sanitizeTitle(titleEditText.getText()),
                            0,
                            "video"
                    ));

                    if (Permissions.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        youtubeManager.downloadVideo(downloadId, downloadsTable, activity.findViewById(android.R.id.content), downloadsPath);
                });

                alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> { });

                AlertDialog createdAlertDialog = alertDialog.create();
                createdAlertDialog.show();

                createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                titleEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        createdAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(titleEditText.getText().toString().length() > 0);
                    }
                });

                youtubeManager.downloadThumbnail(thumbnailImageView);
                youtubeManager.getVideoDetails(titleEditText, endTimeEditText);

                createdAlertDialog.show();
            }
        });
    }

    private void showWifiOnly(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setMessage("Une connexion Wifi est requise pour télécharger une vidéo. Allez dans les paramètres pour retirer ce contrôle.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("OK", (dialogInterface, i) -> {});

        alertDialog.create().show();
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
