package com.theah64.whatsappstatusbrowser.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;

import com.theah64.whatsappstatusbrowser.models.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */

public class StatusManager {

    private static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses");
    private static final int THUMBSIZE = 128;
    private List<Status> imageStatuses, videoStatus;
    private final Callback callback;
    private final Activity activity;

    public StatusManager(Activity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
        genStatuses();
    }

    private static Bitmap getThumbnail(Status status) {
        if (status.isVideo()) {
            return ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        } else {
            return ThumbnailUtils.extractThumbnail(
                    BitmapFactory.decodeFile(status.getFile().getAbsolutePath()),
                    THUMBSIZE,
                    THUMBSIZE);
        }
    }

    private static final Comparator lastModifiedComparator = new Comparator() {
        public int compare(Object o1, Object o2) {

            if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                return -1;
            } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                return +1;
            } else {
                return 0;
            }
        }

    };

    private void genStatuses() {

        //Checking if the status directory exist
        if (STATUS_DIRECTORY.exists()) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] statusFiles = STATUS_DIRECTORY.listFiles();

                    imageStatuses = new ArrayList<>();
                    videoStatus = new ArrayList<>();

                    if (statusFiles != null && statusFiles.length > 0) {

                        Arrays.sort(statusFiles, lastModifiedComparator);


                        //Looping through each status
                        for (final File statusFile : statusFiles) {

                            final Status status = new Status(
                                    statusFile,
                                    statusFile.getName(),
                                    statusFile.getAbsolutePath()
                            );

                            status.setThumbnail(getThumbnail(status));

                            if (status.isVideo()) {
                                videoStatus.add(status);
                            } else {
                                imageStatuses.add(status);
                            }

                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onLoaded();
                            }
                        });

                    } else {
                        onFailedOnUI("No status found!");
                    }


                }
            }).start();

        } else {
            onFailedOnUI("WhatsApp Status directory not found");
        }
    }

    private void onFailedOnUI(final String reason) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.onFailed(reason);
            }
        });
    }

    public List<Status> getPhotoStatuses() {
        return imageStatuses;
    }

    public List<Status> getVideoStatuses() {
        return videoStatus;
    }

    public interface Callback {
        void onLoaded();

        void onFailed(final String reason);
    }

}
