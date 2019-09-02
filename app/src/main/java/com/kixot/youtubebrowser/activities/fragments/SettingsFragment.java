package com.kixot.youtubebrowser.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.kixot.youtubebrowser.R;
import com.kixot.youtubebrowser.UrlManager;
import com.kixot.youtubebrowser.utils.FileUtil;

public class SettingsFragment extends PreferenceFragmentCompat {

    View snackbarView;
    private final int DOWNLOAD_PATH_REQUEST_CODE = 1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Preference downloads_path;

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        snackbarView = getActivity().findViewById(android.R.id.content);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        youtubeUrl();
        downloadsPath();

    }

    private void youtubeUrl() {
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
    }

    private void downloadsPath() {
        downloads_path = findPreference("downloads_path");
        downloads_path.setSummary(sharedPreferences.getString("downloads_path", "content://com.android.externalstorage.documents/tree/primary/%3AYoutubeBrowser"));

        downloads_path.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            startActivityForResult(intent, DOWNLOAD_PATH_REQUEST_CODE);
            return true;
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DOWNLOAD_PATH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            String path = FileUtil.getFullPathFromTreeUri(uri, getActivity());

            if (path.contains("/storage/emulated")) {
                editor.putString("downloads_path", path);
                editor.apply();
                downloads_path.setSummary(path);
            } else {
                Snackbar.make(snackbarView, "La sauvegarde sur carte SD est impossible", Snackbar.LENGTH_LONG).show();
            }

        }
    }
}
