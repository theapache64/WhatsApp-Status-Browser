package com.theah64.whatsappstatusbrowser.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * All the auth needed API request must be passed through this gate way.
 * Created by theapache64 on 12/9/16.
 */
public class APIRequestGateway {

    public static final String KEY_API_KEY = "api_key";

    private static final String X = APIRequestGateway.class.getSimpleName();
    private final Activity activity;
    private TelephonyManager tm;

    private static String getDeviceName() {
        final String manufacturer = Build.MANUFACTURER;
        final String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        } else {
            return manufacturer.toUpperCase() + " " + model;
        }
    }


    public interface APIRequestGatewayCallback {
        void onReadyToRequest(final String apiKey);

        void onFailed(final String reason);
    }

    private final Context context;
    @NonNull
    private final APIRequestGatewayCallback callback;

    private APIRequestGateway(Context context, final Activity activity, @NonNull APIRequestGatewayCallback callback) {
        this.context = context;
        this.activity = activity;
        this.callback = callback;
        execute();
    }

    public APIRequestGateway(final Activity activity, APIRequestGatewayCallback callback) {
        this(activity.getBaseContext(), activity, callback);
    }

    public APIRequestGateway(Context context, APIRequestGatewayCallback callback) {
        this(context, null, callback);
    }


    private void register(final Context context) {

        final ProfileUtils profileUtils = ProfileUtils.getInstance(context);

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint({"HardwareIds", "MissingPermission"})
        final String imei = tm.getDeviceId();
        final String deviceHash = DarKnight.getEncrypted(getDeviceName() + imei);

        final String email = profileUtils.getPrimaryEmail();
        final PrefUtils prefUtils = PrefUtils.getInstance(context);

        //Attaching them with the request
        final Request inRequest = new APIRequestBuilder("/in", null)
                .addParamIfNotNull("name", profileUtils.getDeviceOwnerName())
                .addParam("imei", imei)
                .addParam("device_hash", deviceHash)
                .addParamIfNotNull("email", email)
                .build();

        //Doing API request
        OkHttpUtils.getInstance().getClient().newCall(inRequest).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailed(e.getMessage());
                        }
                    });
                } else {
                    callback.onFailed(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final APIResponse inResp = new APIResponse(OkHttpUtils.logAndGetStringBody(response));
                    final String apiKey = inResp.getJSONObjectData().getString(KEY_API_KEY);

                    //Saving in preference
                    final SharedPreferences.Editor editor = prefUtils.getEditor();
                    editor.putString(KEY_API_KEY, apiKey).commit();

                    if (activity != null) {

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onReadyToRequest(apiKey);
                            }
                        });

                    } else {
                        callback.onReadyToRequest(apiKey);
                    }
                } catch (JSONException | APIResponse.APIException e) {
                    e.printStackTrace();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailed(e.getMessage());
                            }
                        });
                    } else {
                        callback.onFailed(e.getMessage());
                    }
                }
            }
        });

    }

    private void execute() {

        Log.d(X, "Opening gateway...");

        if (NetworkUtils.hasNetwork(context)) {

            Log.i(X, "Has network");

            final PrefUtils prefUtils = PrefUtils.getInstance(context);
            final String apiKey = prefUtils.getPref().getString(KEY_API_KEY, null);

            if (apiKey != null) {

                Log.d(X, "hasApiKey " + apiKey);

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onReadyToRequest(apiKey);
                        }
                    });
                } else {
                    callback.onReadyToRequest(apiKey);
                }

            } else {

                Log.i(X, "Registering victim...");

                //Register victim here
                register(context);
            }

        } else {

            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailed("No network!");
                    }
                });
            } else {
                callback.onFailed("No network!");
            }

            Log.e(X, "Doesn't have APIKEY and no network!");

        }
    }
}
