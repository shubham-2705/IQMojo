package com.iqmojo.base.application;

import android.app.Application;

import com.iqmojo.iq_mojo.utils.FontHelper;


public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        // context=this;

        FontHelper.setDefaultFont(this, "SANS_SERIF", "fonts/medium.OTF");
        FontHelper.setDefaultFont(this, "MONOSPACE", "fonts/sub_heading.OTF");
        FontHelper.setDefaultFont(this, "SERIF", "fonts/heading.OTF");
    }

}
