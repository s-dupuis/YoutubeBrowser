package com.kixot.youtubebrowser;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import com.kixot.youtubebrowser.activities.DownloadsActivity;
import com.kixot.youtubebrowser.utils.Permissions;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView youtubeWebView;
    private UrlManager urlManager;
    private FabManager fabManager;

    public final static String downloadPath = Environment.getExternalStorageDirectory()+ File.separator+"/YoutubeBrowser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        urlManager = new UrlManager(YoutubeManager.url);
        fabManager = new FabManager(this, urlManager);

        fabManager.loadDownloadFabs();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadYoutubeWebView();

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
        youtubeWebView.loadUrl(YoutubeManager.url);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!urlManager.getCurrentUrl().equals(urlManager.getInitialUrl())){
            youtubeWebView.goBack();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(R.string.confirm_exit);

            alertDialog.setPositiveButton(R.string.quit, (dialog, which) -> super.onBackPressed());
            alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> {});

            alertDialog.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_downloads) {
            Intent intent = new Intent(MainActivity.this, DownloadsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
