package com.iqmojo.iq_mojo.analytics;

/**
 * Created by shubhamlamba on 3/24/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.persistence.PrefrenceConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class ManyInstallReferrerReceiver extends BroadcastReceiver {

    private final String TAG = ManyInstallReferrerReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String data = intent.getStringExtra("referrer");
            try {
                data = URLDecoder.decode(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                data = null;
            }
            if (data != null) {
                Map<String, String> paramsMap = new HashMap<>();
                for (String s : data.split("&")) {
                    paramsMap.put(s.split("=")[0], s.split("=")[1]);
                    ShowLog.e(TAG + s.split("=")[0], s.split("=")[1]);
                }
                String utm_source = paramsMap.get(PrefrenceConstants.UTM_SOURCE);
                String utm_medium = paramsMap.get(PrefrenceConstants.UTM_MEDIUM);
//                String utm_campaign = paramsMap.get(PrefrenceConstants.UTM_CAMPAIGN);
                // String utm_term = intent.getStringExtra("utm_term");
                // String utm_content = intent.getStringExtra("utm_content");

                // set all values when direct comes
                IqMojoPrefrences prefs = IqMojoPrefrences.getInstance(context);
                prefs.setString(PrefrenceConstants.UTM_SOURCE, utm_source);
                prefs.setString(PrefrenceConstants.UTM_MEDIUM, utm_medium);
//                prefs.setString(PrefrenceConstants.UTM_CAMPAIGN, utm_campaign);

                new CampaignTrackingReceiver().onReceive(context, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}