package com.theah64.whatsappstatusbrowser.activities.base;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


/**
 * Created by theapache64 on 17/7/17.
 */
public class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
