package com.kixot.youtubebrowser.activities.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.UrlManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    View snackbarView;

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        snackbarView = getActivity().findViewById(android.R.id.content);

        EditTextPreference youtube_url = (EditTextPreference)findPreference("youtube_url");
        youtube_url.setSummary(youtube_url.getText());

        youtube_url.setOnPreferenceChangeListener((preference, o) -> {
            if (UrlManager.isUrlValid(o.toString())) {
                preference.setSummary(o.toString());
                return true;
            } else {
                Snackbar.make(snackbarView, "L'url est invalide", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });
    };

}
