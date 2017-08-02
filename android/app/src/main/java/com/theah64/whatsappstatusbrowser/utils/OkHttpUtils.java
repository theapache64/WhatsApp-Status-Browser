package com.theah64.whatsappstatusbrowser.utils;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by shifar on 23/7/16.
 */
public class OkHttpUtils {

    public static final String METHOD_GET = "GET";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .build();

    private static final String X = OkHttpUtils.class.getSimpleName();
    private static OkHttpUtils instance = new OkHttpUtils();

    private OkHttpUtils() {
    }

    public static OkHttpUtils getInstance() {
        return instance;
    }

    public static String logAndGetStringBody(final Response response) throws IOException {
        final String stringResp = response.body().string();
        Log.d(X, "JSONResponse : " + stringResp);
        return stringResp;
    }

    /**
     * Used to cancel passed calls.
     *
     * @param apiCalls
     */
    public static void cancelCalls(final Call... apiCalls) {
        for (final Call apiCall : apiCalls) {
            cancelCall(apiCall);
        }
    }

    public static void cancelCall(final Call apiCall) {
        if (apiCall != null) {
            Log.d(X, "Cancelling call");
            Log.d(X, "isCallExecuted : " + apiCall.isExecuted());
            apiCall.cancel();
        } else {
            Log.d(X, "API Call is null");
        }
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

}
