package com.theah64.whatsappstatusbrowser.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;

import com.theah64.whatsappstatusbrowser.models.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */

public class StatusManager {

    private static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses");
    private static final int THUMBSIZE = 128;
    private List<Status> imageStatuses, videoStatus;

    public StatusManager(ContentResolver contentResolver) throws StatusException {
        genStatuses(contentResolver);
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

    private void genStatuses(ContentResolver contentResolver) throws StatusException {

        //Checking if the status directory exist
        if (STATUS_DIRECTORY.exists()) {

            File[] statusFiles = STATUS_DIRECTORY.listFiles();
            imageStatuses = new ArrayList<>();
            videoStatus = new ArrayList<>();

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

        } else {
            throw new StatusException("WhatsApp Status directory not found");
        }
    }

    public List<Status> getPhotoStatuses() {
        return imageStatuses;
    }

    public List<Status> getVideoStatuses() {
        return videoStatus;
    }

    public static class StatusException extends Exception {
        public StatusException(String message) {
            super(message);
        }
    }
}
