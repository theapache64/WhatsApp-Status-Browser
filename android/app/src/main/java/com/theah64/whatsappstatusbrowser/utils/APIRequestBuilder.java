package com.theah64.whatsappstatusbrowser.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by shifar on 29/7/16.
 * Utility class to create API request object.
 */
public class APIRequestBuilder {

    //private static final String BASE_URL = "http://keralabadminton.com/api";
    private static final String BASE_URL = "http://theapache64.xyz:8080/mock_api/get_json/wsb";
    private static final String KEY_AUTHORIZATION = "Authorization";
    private static final String X = APIRequestBuilder.class.getSimpleName();
    private final Request.Builder requestBuilder = new Request.Builder();
    private final StringBuilder logBuilder = new StringBuilder();

    private final String url;
    private FormBody.Builder params = new FormBody.Builder();


    public APIRequestBuilder(String route, @Nullable final String apiKey) {

        this.url = BASE_URL + route;
        appendLog("URL", url);

        if (apiKey != null) {
            requestBuilder.addHeader(KEY_AUTHORIZATION, apiKey);
            appendLog(KEY_AUTHORIZATION, apiKey);
        }
    }

    private void appendLog(String key, String value) {
        logBuilder.append(String.format("%s='%s'\n", key, value));
    }

    private APIRequestBuilder addParam(final boolean isAllowNull, final String key, String value) {


        if (isAllowNull) {
            this.params.add(key, value);
            appendLog(key, value);
        } else {

            //value must not be null.
            if (value != null) {
                this.params.add(key, value);
                appendLog(key, value);
            }
        }


        return this;
    }

    public APIRequestBuilder addParam(final String key, final String value) {
        return addParam(true, key, value);
    }

    public APIRequestBuilder addParam(final String key, final boolean value) {
        return addParam(true, key, value ? "1" : "0");
    }

    /**
     * Used to build the OkHttpRequest.
     */
    public Request build() {

        requestBuilder
                .url(url)
                .post(params.build());

        Log.d(X, "Request : " + logBuilder.toString());

        return requestBuilder.build();
    }

    public APIRequestBuilder addOptionalParam(String key, String value) {
        return addParam(false, key, value);
    }


}
