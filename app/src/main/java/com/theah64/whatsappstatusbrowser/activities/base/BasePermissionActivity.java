package com.theah64.whatsappstatusbrowser.activities.base;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.theah64.whatsappstatusbrowser.utils.PermissionUtils;


/**
 * Created by theapache64 on 6/1/17.
 */

public abstract class BasePermissionActivity extends BaseAppCompatActivity implements PermissionUtils.Callback {

    private static final String X = BasePermissionActivity.class.getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
        new PermissionUtils(this, this, this).begin();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PermissionUtils.RQ_CODE_ASK_PERMISSION) {

            Log.d(X, "Grant result length: " + grantResults.length);

            boolean isAllPermissionGranted = true;
            for (final int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    isAllPermissionGranted = false;
                    break;
                }
            }

            if (isAllPermissionGranted) {
                onAllPermissionGranted();
            } else {
                onPermissionDenial();
            }
        }
    }
}
