package com.theah64.whatsappstatusbrowser.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.activities.base.BaseAppCompatActivity;
import com.theah64.whatsappstatusbrowser.adapters.PagerAdapter;
import com.theah64.whatsappstatusbrowser.utils.PermissionUtils;

public class MainActivity extends BaseAppCompatActivity implements PermissionUtils.Callback {


    private ViewPager vpStatuses;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new PermissionUtils(this, this, this).begin();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PermissionUtils.RQ_CODE_ASK_PERMISSION) {

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

    @Override
    public void onAllPermissionGranted() {
        vpStatuses = (ViewPager) findViewById(R.id.vpStatuses);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        vpStatuses.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpStatuses);
    }

    @Override
    public void onPermissionDenial() {
        Toast.makeText(this, R.string.Insufficient_app_permission, Toast.LENGTH_SHORT).show();
        finish();
    }
}
