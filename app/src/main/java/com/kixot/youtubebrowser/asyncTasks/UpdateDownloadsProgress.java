package com.kixot.youtubebrowser.asyncTasks;

import android.os.AsyncTask;

import com.kixot.youtubebrowser.activities.DownloadsActivity;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.models.Download;

import java.util.ArrayList;

public class UpdateDownloadsProgress extends AsyncTask<Void, Void, ArrayList<Download>> {

    private DownloadsTable downloadsTable;
    private DownloadsActivity downloadsActivity;

    public UpdateDownloadsProgress(DownloadsTable downloadsTable, DownloadsActivity activity) {
        this.downloadsTable = downloadsTable;
        this.downloadsActivity = activity;
    }

    @Override
    protected ArrayList<Download> doInBackground(Void... params) {
        return downloadsTable.getDownloads();
    }

    @Override
    protected void onPostExecute(ArrayList<Download> downloads) {
        downloadsActivity.updateList(downloads);
        super.onPostExecute(downloads);
    }
}
