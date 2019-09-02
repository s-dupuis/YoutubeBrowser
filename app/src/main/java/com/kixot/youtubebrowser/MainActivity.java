package com.kixot.youtubebrowser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.kixot.youtubebrowser.activities.AboutActivity;
import com.kixot.youtubebrowser.activities.DownloadsActivity;
import com.kixot.youtubebrowser.activities.SettingsActivity;
import com.kixot.youtubebrowser.bdd.tables.DownloadsTable;
import com.kixot.youtubebrowser.utils.Permissions;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView youtubeWebView;
    private UrlManager urlManager;
    private FabManager fabManager;

    private DownloadsTable downloadsTable;

    public String preference_YoutubeURL;
    public boolean preference_WifiOnly;
    public String preference_downloadsPath;

    //public String downloadPath = Environment.getExternalStorageDirectory()+ File.separator+"/YoutubeBrowser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        preference_YoutubeURL = sharedPreferences.getString("youtube_url", "https://m.youtube.com/");
        preference_WifiOnly = sharedPreferences.getBoolean("wifi_download", true);
        preference_downloadsPath = sharedPreferences.getString("downloads_path", "content://com.android.externalstorage.documents/tree/primary/%3AYoutubeBrowser");

        urlManager = new UrlManager(preference_YoutubeURL);
        fabManager = new FabManager(this, urlManager);

        fabManager.loadDownloadFabs();
        loadYoutubeWebView();

        downloadsTable = new DownloadsTable(this);
        downloadsTable.open();
        if (savedInstanceState == null) downloadsTable.cancelDownloads();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void requestPermissions (String[] permissions) {
        for (String permission : permissions) Permissions.requestPermission(this, permission);
    }

    private void loadYoutubeWebView()  {
        youtubeWebView = (WebView) findViewById(R.id.youtubeWebView);

        youtubeWebView.setWebViewClient(new YoutubeWebViewClient(urlManager, fabManager));
        youtubeWebView.getSettings().setLoadsImagesAutomatically(true);
        youtubeWebView.getSettings().setJavaScriptEnabled(true);
        youtubeWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        youtubeWebView.loadUrl(preference_YoutubeURL);
    }

    public boolean checkHasWifi() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final Network network = connectivityManager.getActiveNetwork();

        if (network != null) {
            final NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(network);
            return nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
        return false;
    }

    private void exitApp(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.confirm_exit);

        alertDialog.setPositiveButton(R.string.quit, (dialog, which) -> super.onBackPressed());
        alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> {});

        alertDialog.create().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!urlManager.getCurrentUrl().equals(urlManager.getInitialUrl())){
            youtubeWebView.goBack();
        } else {
            exitApp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            if (!this.youtubeWebView.getUrl().equals(preference_YoutubeURL)) {
                this.youtubeWebView.clearHistory();
                this.youtubeWebView.loadUrl(preference_YoutubeURL);
            }
            return true;
        } else if (id == R.id.action_refresh) {
            this.youtubeWebView.reload();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;

        switch(id) {
            case R.id.nav_downloads:
                intent = new Intent(MainActivity.this, DownloadsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_poweroff:
                exitApp();
                break;
            case R.id.nav_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
