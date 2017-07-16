package com.theah64.whatsappstatusbrowser.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
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
    private List<Status> imageStatuses, videoStatus;

    public StatusManager(ContentResolver contentResolver) throws StatusException {
        genStatuses(contentResolver);
    }

    private static Bitmap getThumbnail(ContentResolver cr, String path) {
        Bitmap bmpThumb = null;
        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns._ID}, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (ca != null) {
            if (ca.moveToFirst()) {
                int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
                bmpThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
            }
            ca.close();
        }

        return bmpThumb;

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
                        getThumbnail(contentResolver, statusFile.getAbsolutePath()),
                        statusFile.getName(),
                        statusFile.getAbsolutePath()
                );

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
