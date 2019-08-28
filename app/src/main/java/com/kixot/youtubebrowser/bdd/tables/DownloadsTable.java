package com.kixot.youtubebrowser.bdd.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kixot.youtubebrowser.bdd.BaseYoutubeBrowser;
import com.kixot.youtubebrowser.models.Download;

import java.util.ArrayList;

public class DownloadsTable {

    private final BaseYoutubeBrowser baseYoutubeBrowser;
    private SQLiteDatabase bdd;

    private final String TABLE_NAME = "downloads";
    private final String FIELD_ID = "idDownload";
    private final int FIELD_ID_INDEX = 0;
    private final String FIELD_TITLE = "title";
    private final int FIELD_TITLE_INDEX = 1;
    private final String FIELD_PROGRESS = "progress";
    private final int FIELD_PROGRESS_INDEX = 2;
    private final String FIELD_TYPE = "type";
    private final int FIELD_TYPE_INDEX = 3;

    public DownloadsTable(Context c) {
        baseYoutubeBrowser = new BaseYoutubeBrowser(c);
    }

    public void open() {
        bdd = baseYoutubeBrowser.getWritableDatabase();
    }

    public long insertDownload(Download download) {
        ContentValues values = new ContentValues();

        values.put(FIELD_TITLE, download.getTitle());
        values.put(FIELD_PROGRESS, download.getProgress());
        values.put(FIELD_TYPE, download.getType());

        return bdd.insert(TABLE_NAME, null, values);
    }

    public Download getDownload(long id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = ?;";
        Cursor c = bdd.rawQuery(query, new String[]{Long.toString(id)});
        return cursorToDownload(c);
    }

    public ArrayList<Download> getDownloads() {
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = bdd.rawQuery(query, new String[]{});
        return cursorToListDownload(c);
    }

    public boolean deleteDownlaod(long id) {
        return bdd.delete(TABLE_NAME, FIELD_ID + " = ?;", new String[]{ id+"" }) > 0;
    }

    public boolean updateProgress(long id, int progress){
        ContentValues values = new ContentValues();

        values.put(FIELD_PROGRESS, progress);

        return bdd.update(TABLE_NAME, values, FIELD_ID + "=?;", new String[]{ id+"" }) > 0;
    }

    private Download cursorToDownload(Cursor c) {
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        c.moveToFirst();

        Download download = new Download(
                c.getLong(FIELD_ID_INDEX),
                c.getString(FIELD_TITLE_INDEX),
                c.getInt(FIELD_PROGRESS_INDEX),
                c.getString(FIELD_TYPE_INDEX)
        );

        c.close();

        return download;
    }

    private ArrayList<Download> cursorToListDownload(Cursor c) {
        ArrayList<Download> downloads = new ArrayList<>();

        if (c.getCount() == 0){
            c.close();
            return downloads;
        }

        c.moveToFirst();

        do{
            downloads.add(new Download(
                    c.getLong(FIELD_ID_INDEX),
                    c.getString(FIELD_TITLE_INDEX),
                    c.getInt(FIELD_PROGRESS_INDEX),
                    c.getString(FIELD_TYPE_INDEX)
            ));
        }while(c.moveToNext());

        c.close();

        return downloads;
    }

    public void clearTable() {
        bdd.delete(TABLE_NAME, null, null);
    }
}