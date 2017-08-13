package com.iqmojo.base.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;


public class SystemUtil {


    public static String getOsVersion() {

        String[] mapper = new String[]{
                "ANDROID BASE", "ANDROID BASE 1.1", "CUPCAKE", "DONUT",
                "ECLAIR", "ECLAIR_0_1", "ECLAIR_MR1", "FROYO", "GINGERBREAD",
                "GINGERBREAD_MR1", "HONEYCOMB", "HONEYCOMB_MR1", "HONEYCOMB_MR2",
                "ICE_CREAM_SANDWICH", "ICE_CREAM_SANDWICH_MR1", "JELLY_BEAN",
                "JELLY_BEAN_MR1", "JELLY_BEAN_MR2", "KITKAT", "KITKAT_WATCH",
                "LOLLIPOP", "LOLLIPOP_MR1", "M"
        };
        int index = Build.VERSION.SDK_INT - 1;
        return index < mapper.length ? mapper[index] : "UNKNOWN_VERSION"; // >

    }

    public static String getDeviceId(Context context) {

        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

//    private boolean isAppForeground(Context mContext) {
//
//        ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> l = mActivityManager
//                .getRunningAppProcesses();
//        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
//        while (i.hasNext()) {
//            ActivityManager.RunningAppProcessInfo info = i.next();
//
//            if (info.uid == mContext.getApplicationInfo().uid && info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                return true;
//            }
//        }
//        return false;
//    }

}
