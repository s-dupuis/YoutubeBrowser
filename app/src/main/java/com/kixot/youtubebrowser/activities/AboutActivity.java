package com.kixot.youtubebrowser.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kixot.youtubebrowser.activities.fragments.AboutFragment;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new AboutFragment()).commit();
    }

}