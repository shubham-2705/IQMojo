package com.iqmojo.iq_mojo.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        IqMojoPrefrences.getInstance(getApplicationContext()).setString(AppConstants.KEY_FCM_ID, refreshedToken);
        IqMojoPrefrences.getInstance(getApplicationContext()).setBoolean(AppConstants.KEY_FCM_ID_UPDATED, true);
//        Intent intent = new Intent(this, RegistrationIntentService.class);
//        startService(intent);
    }
}