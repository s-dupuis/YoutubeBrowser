package com.kixot.youtubebrowser.utils;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.kixot.youtubebrowser.MainActivity;

public class Permissions {

    public static boolean checkPermission(MainActivity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean requestPermission(MainActivity activity, String permission) {
        if (checkPermission(activity, permission)) return true;
        else ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);

        return checkPermission(activity, permission);
    }

}
