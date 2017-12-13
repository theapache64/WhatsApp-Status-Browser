package com.theah64.whatsappstatusbrowser.utils;

import android.os.Build;

/**
 * Created by theapache64 on 14/12/16.
 */

public class CommonUtils {

    private static final String X = CommonUtils.class.getSimpleName();

    static boolean isSupport(final int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

}
