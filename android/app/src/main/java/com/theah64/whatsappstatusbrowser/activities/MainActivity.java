package com.theah64.whatsappstatusbrowser.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.activities.base.BaseAppCompatActivity;
import com.theah64.whatsappstatusbrowser.adapters.PagerAdapter;
import com.theah64.whatsappstatusbrowser.utils.PermissionUtils;

public class MainActivity extends BaseAppCompatActivity implements PermissionUtils.Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new PermissionUtils(this, this, this).begin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.About);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Tribute :P
        Toast.makeText(this, "Developed by theapache64", Toast.LENGTH_SHORT).show();

        final Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://github.com/theapache64/WhatsApp-Status-Browser"));
        startActivity(i);
        return true;
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
        ViewPager vpStatuses = (ViewPager) findViewById(R.id.vpStatuses);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        vpStatuses.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpStatuses);
    }

    @Override
    public void onPermissionDenial() {
        Toast.makeText(this, R.string.Insufficient_app_permission, Toast.LENGTH_SHORT).show();
        finish();
    }
}
