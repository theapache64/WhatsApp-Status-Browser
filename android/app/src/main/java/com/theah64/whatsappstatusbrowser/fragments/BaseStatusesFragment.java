package com.theah64.whatsappstatusbrowser.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.adapters.StatusAdapter;
import com.theah64.whatsappstatusbrowser.models.Status;
import com.theah64.whatsappstatusbrowser.utils.DialogUtils;
import com.theah64.whatsappstatusbrowser.utils.StatusManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseStatusesFragment extends Fragment implements StatusAdapter.Callback, StatusManager.Callback {

    private StatusManager statusManager;
    private RecyclerView rvStatuses;
    private DialogUtils dialogUtils;
    private AlertDialog loadingDialog;

    public BaseStatusesFragment() {
        // Required empty public constructor
    }

    protected StatusManager getStatusManager() {
        return statusManager;
    }

    public abstract List<Status> getStatuses();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootLayout = inflater.inflate(R.layout.fragment_statuses, container, false);
        rvStatuses = (RecyclerView) rootLayout.findViewById(R.id.rvStatuses);
        rvStatuses.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        dialogUtils = new DialogUtils(getActivity());
        loadingDialog = dialogUtils.getLoadingDialog(R.string.Loading);
        loadingDialog.show();

        statusManager = new StatusManager(getActivity(), this);

        return rootLayout;
    }

    @Override
    public void onItemClicked(int position) {
        final Status status = getStatuses().get(position);
        final String filePath = "file://" + status.getFile().getAbsolutePath();

        if (status.isVideo()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "video/mp4");
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "image/jpg");
            startActivity(intent);
        }
    }

    private static final String APP_DIR = Environment.getExternalStorageDirectory() + File.separator + "WhatsAppStatuses";

    @Override
    public void onSaveToGalleryClicked(int position) {

        final File appFolder = new File(APP_DIR);
        if (!appFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            appFolder.mkdirs();
        }

        final Status status = getStatuses().get(position);
        final File destFile = new File(appFolder + File.separator + status.getTitle());

        if (destFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            destFile.delete();
        }

        try {
            copyFile(status.getFile(), destFile);
            Toast.makeText(getActivity(), R.string.Saved_to_gallery, Toast.LENGTH_SHORT).show();

            Intent intent =
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(destFile));
            getActivity().sendBroadcast(intent);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.Failed_to_save_to_gallery, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void copyFile(File sourceFile, File destFile) throws IOException {

        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public void onLoaded() {
        loadingDialog.dismiss();
        rvStatuses.setAdapter(new StatusAdapter(getActivity(), getStatuses(), BaseStatusesFragment.this));
    }

    @Override
    public void onFailed(String reason) {
        loadingDialog.dismiss();
        dialogUtils.showErrorDialog(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
    }
}
