package com.kixot.youtubebrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.adapters.DownloadListViewAdapter;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;

import java.util.ArrayList;

public class DownloadsActivity extends AppCompatActivity {

    private DownloadsTable downloadsTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        downloadsTable = new DownloadsTable(this);
        downloadsTable.open();

        loadDownloadsList();
    }

    private void loadDownloadsList () {
        ListView downloadsListView = (ListView) findViewById(R.id.downloadsListView);

        ArrayList<Download> downloads = downloadsTable.getDownloads();
        downloadsListView.setAdapter(new DownloadListViewAdapter(this, downloads));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
