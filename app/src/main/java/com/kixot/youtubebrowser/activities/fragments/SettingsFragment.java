package com.kixot.youtubebrowser.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.kixot.youtubebrowser.utils.FileUtils;

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
        resetSettings();

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
        downloads_path.setSummary(sharedPreferences.getString("downloads_path", "/storage/0/sdcard/YoutubeBrowser"));

        downloads_path.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            startActivityForResult(intent, DOWNLOAD_PATH_REQUEST_CODE);
            return true;
        });

    }

    private void resetSettings() {
        Preference reset_settings = findPreference("reset_settings");

        reset_settings.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setMessage("Voulez-vous restaurer les paramètres par défaut ?");
            alertDialog.setCancelable(true);

            alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> {});
            alertDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                editor.clear();
                editor.apply();

                Snackbar.make(snackbarView, "Les paramètres ont été mis à jour.", Snackbar.LENGTH_SHORT).show();
            });

            alertDialog.create().show();

            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DOWNLOAD_PATH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            String path = FileUtils.getFullPathFromTreeUri(uri, getActivity());

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
