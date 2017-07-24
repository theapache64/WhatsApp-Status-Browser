package com.theah64.whatsappstatusbrowser.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.theah64.whatsappstatusbrowser.fragments.PhotoStatusesFragment;
import com.theah64.whatsappstatusbrowser.fragments.VideoStatusesFragment;

/**
 * Created by theapache64 on 17/7/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private final PhotoStatusesFragment photoStatusesFragment;
    private final VideoStatusesFragment videoStatusesFragment;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        photoStatusesFragment = new PhotoStatusesFragment();
        videoStatusesFragment = new VideoStatusesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? photoStatusesFragment : videoStatusesFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "PHOTO" : "VIDEO";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
