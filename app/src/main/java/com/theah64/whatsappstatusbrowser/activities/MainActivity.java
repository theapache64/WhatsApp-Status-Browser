package com.theah64.whatsappstatusbrowser.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.activities.base.BasePermissionActivity;
import com.theah64.whatsappstatusbrowser.adapters.PagerAdapter;

public class MainActivity extends BasePermissionActivity {


    private ViewPager vpStatuses;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        vpStatuses = (ViewPager) findViewById(R.id.vpStatuses);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

    }

    @Override
    public void onAllPermissionGranted() {
        vpStatuses.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpStatuses);
    }

    @Override
    public void onPermissionDenial() {
        Toast.makeText(this, R.string.Insufficient_app_permission, Toast.LENGTH_SHORT).show();
        finish();
    }
}
