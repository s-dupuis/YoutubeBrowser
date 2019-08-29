package com.kixot.youtubebrowser.bdd;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseYoutubeBrowser extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "youtube_browser";
    private final static int DATABASE_VERSION = 1;

    private final String TABLE_DOWNLOADS = "downloads";

    private final String TABLE_CREATE_DOWNLOADS =
            "CREATE TABLE IF NOT EXISTS "+TABLE_DOWNLOADS+" ("+
                    "idDownload INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "title VARCHAR(255),"+
                    "progress INTEGER,"+
                    "type VARCHAR(50)," +
                    "status VARCHAR(50));";

    public BaseYoutubeBrowser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DOWNLOADS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
