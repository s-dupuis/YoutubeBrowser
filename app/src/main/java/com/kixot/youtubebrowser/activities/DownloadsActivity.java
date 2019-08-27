package com.kixot.youtubebrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.adapters.DownloadListViewAdapter;
import com.kixot.youtubebrowser.models.Download;

import java.util.ArrayList;

public class DownloadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadDownloadsList();
    }

    private void loadDownloadsList () {
        ListView downloadsListView = (ListView) findViewById(R.id.downloadsListView);

        ArrayList<Download> downloads = new ArrayList<>();
        downloads.add(new Download(1, "Crossfaith - Xenoooooooooooooooooooooooooo", 33, "audio"));
        downloads.add(new Download(2, "Crossfaith - System X", 42, "audio"));
        downloads.add(new Download(3, "Crossfaith - Anger", 25, "video"));
        downloads.add(new Download(4,"Crossfaith - Calm The Storm", 75, "audio"));
        downloads.add(new Download(5,"Crossfaith - Tears Fall", 87, "audio"));
        downloads.add(new Download(6,"Crossfaith - Wildfire", 100, "video"));
        downloads.add(new Download(7,"Crossfaith - Vermillion", 0, "audio"));

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
