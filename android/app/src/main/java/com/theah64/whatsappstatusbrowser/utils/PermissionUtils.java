
package com.theah64.whatsappstatusbrowser.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

/**
 * Created by theapache64 on 5/1/17.
 */

public class PermissionUtils {

    public static final int RQ_CODE_ASK_PERMISSION = 1;
    
    private static final String[] PERMISSIONS_NEEDED = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final Context context;
    private final Callback callback;
    private final Activity activity;

    public PermissionUtils(Context context, Callback callback, Activity activity) {
        this.context = context;
        this.callback = callback;
        this.activity = activity;
    }

    public void begin() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean isAllPermissionAccepted = true;
            for (final String perm : PERMISSIONS_NEEDED) {
                if (ActivityCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                    isAllPermissionAccepted = false;
                    break;
                }
            }

            if (!isAllPermissionAccepted) {
                if (activity != null) {
                    activity.requestPermissions(PERMISSIONS_NEEDED, RQ_CODE_ASK_PERMISSION);
                } else {
                    callback.onPermissionDenial();
                }
            } else {
                callback.onAllPermissionGranted();
            }

        } else {
            callback.onAllPermissionGranted();
        }

    }

    public interface Callback {
        void onAllPermissionGranted();

        void onPermissionDenial();
    }
}
