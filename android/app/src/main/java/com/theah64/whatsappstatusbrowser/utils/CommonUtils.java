package com.theah64.whatsappstatusbrowser.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by theapache64 on 14/12/16.
 */

public class CommonUtils {

    private static final String X = CommonUtils.class.getSimpleName();

    static boolean isSupport(final int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }


    public static String getMIMETypeFromUrl(final File file, final String defaultValue) {

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        int index = file.getName().lastIndexOf('.') + 1;
        String ext = file.getName().substring(index).toLowerCase();
        final String mimeType = mime.getMimeTypeFromExtension(ext);

        if (mimeType != null) {
            return mimeType;
        }

        return defaultValue;
    }

    public static boolean isMyServiceRunning(final Context context, Class<?> serviceClass) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : am.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(X, "Service running : " + serviceClass.getName());
                return true;
            }
        }

        Log.e(X, "Service not running : " + serviceClass.getName());
        return false;
    }

    public static String getSanitizedName(final String fileName) {
        return fileName.replaceAll("[^\\w]", "_").replaceAll("[_]{2,}", "_");
    }

    public static void open(Activity activity, File file) {
        //Opening audio file
        final Intent playIntent = new Intent(Intent.ACTION_VIEW);
        playIntent.setDataAndType(UriCompat.fromFile(activity, file), "audio/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            playIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        activity.startActivity(playIntent);
    }
}
