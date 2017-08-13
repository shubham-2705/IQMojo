package com.iqmojo.base.application;

import android.app.Application;



public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        // context=this;
    }

}
