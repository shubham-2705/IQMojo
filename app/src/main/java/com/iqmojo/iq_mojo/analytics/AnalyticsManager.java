package com.iqmojo.iq_mojo.analytics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.iqmojo.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.google.firebase.perf.FirebasePerformance;

/**
 * Created by shubhamlamba on 27/07/17.
 */

public class AnalyticsManager {

    private static AnalyticsManager mAnalyticsManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String TAG = AnalyticsManager.class.getSimpleName();

    private AnalyticsManager() {
        // do nothing
    }

    private AnalyticsManager(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static AnalyticsManager getInstance(Context context) {
        if (mAnalyticsManager == null)
            mAnalyticsManager = new AnalyticsManager(context);

        return mAnalyticsManager;
    }

    public void logFirebaseEvent(String screenName, int timeSpentInSec) {
        if (BuildConfig.ANALYTICS_ENABLED) {
            Bundle bundle = new Bundle();
            if (timeSpentInSec > 0)
                bundle.putDouble(FirebaseAnalytics.Param.VALUE, timeSpentInSec);
            mFirebaseAnalytics.logEvent(screenName, bundle);
        }
    }

    public void logFirebaseEvent(String screenName, Bundle bunddle) {
        if (BuildConfig.ANALYTICS_ENABLED) {
            mFirebaseAnalytics.logEvent(screenName, bunddle);
        }
    }


}


