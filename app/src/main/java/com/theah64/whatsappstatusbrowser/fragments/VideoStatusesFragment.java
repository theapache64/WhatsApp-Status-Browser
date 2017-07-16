package com.theah64.whatsappstatusbrowser.fragments;

import com.theah64.whatsappstatusbrowser.models.Status;

import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */

public class VideoStatusesFragment extends BaseStatusesFragment {
    @Override
    public List<Status> getStatuses() {
        return getStatusManager().getVideoStatuses();
    }
}
