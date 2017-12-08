package com.theah64.whatsappstatusbrowser.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by theapache64 on 8/12/17.
 */

public class GenericFileProvider extends FileProvider {

    private static final String AUTHORITY = "com.theah64.whatsappstatusbrowser.provider";

    public static Uri getUriForFileWithAuthority(Context context, File file) {
        return getUriForFile(context, AUTHORITY, file);
    }
}
