package com.theah64.whatsappstatusbrowser.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by theapache64 on 11/9/16.
 */
public class ProfileUtils {

    private final Context context;
    private static ProfileUtils instance;

    private ProfileUtils(Context context) {
        this.context = context;
    }

    public static ProfileUtils getInstance(final Context context) {
        if (instance == null) {
            instance = new ProfileUtils(context.getApplicationContext());
        }
        return instance;
    }


    public String getDeviceOwnerName() {

        if (CommonUtils.isSupport(14)) {
            final Cursor c = context.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                final String ownerName = c.getString(c.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME));
                c.close();
                return ownerName;
            }

        }

        return null;
    }


    public String getPrimaryEmail() {
        return getAccountName(Patterns.EMAIL_ADDRESS);
    }

    private String getAccountName(final Pattern pattern) {
        final Account[] accounts = AccountManager.get(context).getAccounts();
        for (final Account account : accounts) {
            if (pattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }


}
