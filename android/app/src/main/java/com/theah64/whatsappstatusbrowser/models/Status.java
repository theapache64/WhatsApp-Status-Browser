package com.theah64.whatsappstatusbrowser.models;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by theapache64 on 16/7/17.
 */

public class Status {
    private static final String MP4 = ".mp4";
    private final File file;
    private Bitmap thumbnail;
    private final String title, subtitle;
    private final boolean isVideo;

    public Status(File file, String title, String subtitle) {
        this.file = file;
        this.title = title;
        this.subtitle = subtitle;
        this.isVideo = file.getName().endsWith(MP4);
    }


    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public File getFile() {
        return file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isVideo() {
        return isVideo;
    }
}
