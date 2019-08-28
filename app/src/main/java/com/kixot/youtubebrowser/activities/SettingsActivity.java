package com.kixot.youtubebrowser.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kixot.youtubebrowser.activities.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    public final static String KEY_PREF_YOUTUBE_URL = "youtube_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
