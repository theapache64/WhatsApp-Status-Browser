package com.theah64.whatsappstatusbrowser.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.theah64.whatsappstatusbrowser.R;


/**
 * Created by theapache64 on 15/5/17.
 */

public class DialogUtils {
    private final Context context;
    private LayoutInflater inflater;

    public DialogUtils(final Context context) {
        this.context = context;
    }


    /**
     * Shows a error dialog with a single 'OK' button and the given error message.
     *
     * @param errorMessage error message explaining the error.
     * @param callback
     */
    public void showErrorDialog(final String errorMessage, @Nullable DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(context)
                .setTitle(null)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setNeutralButton(context.getString(android.R.string.ok), callback)
                .show();
    }


    /**
     * To show a loading dialog.
     *
     * @param message message to be displayed on the dialog.
     * @return an instance of the showing dialog.
     */
    public AlertDialog getLoadingDialog(final @StringRes int message) {
        return getLoadingDialog(false, context.getString(message));
    }

    private AlertDialog getLoadingDialog(final boolean isCancellable, String message) {

        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }

        @SuppressLint("InflateParams")
        final View loadingDialogue = inflater.inflate(R.layout.loading_dialog, null);

        //Setting message
        ((TextView) loadingDialogue.findViewById(R.id.tvMessage))
                .setText(message);

        return new AlertDialog.Builder(context)
                .setView(loadingDialogue)
                .setCancelable(isCancellable)
                .create();
    }
}
