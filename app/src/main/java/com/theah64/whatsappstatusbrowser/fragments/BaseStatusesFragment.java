package com.theah64.whatsappstatusbrowser.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.models.Status;
import com.theah64.whatsappstatusbrowser.utils.StatusManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseStatusesFragment extends Fragment {

    private StatusManager statusManager;

    public BaseStatusesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            statusManager = new StatusManager(getContext().getContentResolver());
        } catch (StatusManager.StatusException e) {
            e.printStackTrace();
        }
    }

    protected StatusManager getStatusManager() {
        return statusManager;
    }

    public abstract List<Status> getStatuses();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statuses, container, false);
    }

}
