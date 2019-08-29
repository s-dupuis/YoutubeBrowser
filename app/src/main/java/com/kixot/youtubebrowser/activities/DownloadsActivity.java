package com.kixot.youtubebrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.adapters.DownloadListViewAdapter;
import com.kixot.youtubebrowser.asyncTasks.UpdateDownloadsProgress;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadsActivity extends AppCompatActivity {

    private DownloadsTable downloadsTable;
    private ArrayList<Download> downloads;
    private DownloadListViewAdapter downloadListViewAdapter;
    ListView downloadsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        downloadsTable = new DownloadsTable(this);
        downloadsTable.open();

        downloads = downloadsTable.getDownloads();
        loadDownloadsList();
    }

    private void loadDownloadsList () {
        downloadsListView = (ListView) findViewById(R.id.downloadsListView);

        downloads = downloadsTable.getDownloads();
        downloadListViewAdapter = new DownloadListViewAdapter(this, downloads, downloadsTable);

        downloadsListView.setAdapter(downloadListViewAdapter);

        updateDownloadsProgress();
    }

    private void updateDownloadsProgress () {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                UpdateDownloadsProgress task = new UpdateDownloadsProgress(downloadsTable, DownloadsActivity.this);
                task.execute();
            }

        },0,1000);
    }

    public void updateList (ArrayList<Download> downloads) {
        this.downloads.clear();
        this.downloads.addAll(downloads);
        ((DownloadListViewAdapter)downloadsListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.downloads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        } else if (id == R.id.action_clear) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(true);
            dialog.setMessage("Supprimer les téléchargements terminés ?");

            dialog.setNegativeButton(R.string.no, (dialog1, which) -> { });
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> downloadsTable.deleteOldDownloads());

            dialog.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
