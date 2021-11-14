package com.theah64.whatsappstatusbrowser.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
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
    public void onAllPermissionGranted() {
        ViewPager vpStatuses = (ViewPager) findViewById(R.id.vpStatuses);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        vpStatuses.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpStatuses);

        Toast.makeText(this, R.string.main_ins, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenial() {
        Toast.makeText(this, R.string.Insufficient_app_permission, Toast.LENGTH_SHORT).show();
        finish();
    }
}
